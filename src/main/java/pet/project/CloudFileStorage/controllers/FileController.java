package pet.project.CloudFileStorage.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pet.project.CloudFileStorage.dto.file.FileDownloadDto;
import pet.project.CloudFileStorage.exceptions.file.InvalidFileDownloadException;
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
    public ResponseEntity<ByteArrayResource> downloadFile(@ModelAttribute("fileDownloadDto")
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
}
