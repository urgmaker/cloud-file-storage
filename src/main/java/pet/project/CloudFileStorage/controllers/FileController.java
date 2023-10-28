package pet.project.CloudFileStorage.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pet.project.CloudFileStorage.dto.file.FileDeleteDto;
import pet.project.CloudFileStorage.dto.file.FileDownloadDto;
import pet.project.CloudFileStorage.dto.file.FileRenameDto;
import pet.project.CloudFileStorage.dto.file.FileUploadDto;
import pet.project.CloudFileStorage.exceptions.file.InvalidFileDeleteException;
import pet.project.CloudFileStorage.exceptions.file.InvalidFileDownloadException;
import pet.project.CloudFileStorage.exceptions.file.InvalidFileRenameException;
import pet.project.CloudFileStorage.exceptions.file.InvalidFileUploadException;
import pet.project.CloudFileStorage.services.FileService;
import pet.project.CloudFileStorage.utils.ResponseError;

@Controller
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<ByteArrayResource> downloadFile(@Valid @ModelAttribute("fileDownloadDto")
                                                          FileDownloadDto fileDownloadDto,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFileDownloadException(ResponseError.getErrorMessage(bindingResult));
        }

        ByteArrayResource file = fileService.downloadFile(fileDownloadDto);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + fileDownloadDto.getName())
                .body(file);
    }

    @PostMapping
    public RedirectView uploadFile(@Valid @ModelAttribute("fileUploadDto") FileUploadDto fileUploadDto,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFileUploadException(ResponseError.getErrorMessage(bindingResult));
        }
        fileService.uploadFile(fileUploadDto);

        redirectAttributes.addFlashAttribute("success", "File uploaded successfully");
        return new RedirectView("/");
    }

    @PutMapping
    public RedirectView renameFile(@Valid @ModelAttribute("fileRenameDto") FileRenameDto fileRenameDto,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFileRenameException(ResponseError.getErrorMessage(bindingResult));
        }
        fileService.renameFile(fileRenameDto);

        redirectAttributes.addFlashAttribute("success", "File renamed successfully");
        return new RedirectView("/");
    }

    @DeleteMapping
    public RedirectView deleteFile(@Valid @ModelAttribute("fileDeleteDto") FileDeleteDto fileDeleteDto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFileDeleteException(ResponseError.getErrorMessage(bindingResult));
        }
        fileService.deleteFile(fileDeleteDto);

        redirectAttributes.addFlashAttribute("success", "File deleted successfully");
        return new RedirectView("/");
    }
}
