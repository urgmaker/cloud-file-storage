package pet.project.CloudFileStorage.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pet.project.CloudFileStorage.dto.folder.FolderRenameDto;
import pet.project.CloudFileStorage.dto.folder.FolderUploadDto;
import pet.project.CloudFileStorage.exceptions.folder.InvalidFolderRenameException;
import pet.project.CloudFileStorage.exceptions.folder.InvalidFolderUploadException;
import pet.project.CloudFileStorage.services.FolderService;
import pet.project.CloudFileStorage.utils.ResponseError;

@Controller
@RequestMapping("/folders")
public class FolderController {
    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping
    public RedirectView uploadFolder(@Valid @ModelAttribute("folderUploadDto") FolderUploadDto folderUploadDto,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (isFolderEmpty(folderUploadDto)) {
            throw new InvalidFolderUploadException("Empty folder upload is not supported");
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidFolderUploadException(ResponseError.getErrorMessage(bindingResult));
        }

        folderService.uploadFolder(folderUploadDto);

        redirectAttributes.addFlashAttribute("success", "Folder uploaded successfully");
        return new RedirectView("/");
    }

    @PostMapping
    public RedirectView renameFolder(@Valid @ModelAttribute("folderRenameDto") FolderRenameDto folderRenameDto,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            throw new InvalidFolderRenameException(ResponseError.getErrorMessage(bindingResult));
        }
        folderService.renameFolder(folderRenameDto);

        redirectAttributes.addFlashAttribute("success", "Folder renamed successfully");
        return new RedirectView("/");
    }

    private boolean isFolderEmpty(FolderUploadDto folderUploadDto) {
        if (folderUploadDto.getFiles().size() == 1) {
            String filename = folderUploadDto.getFiles().get(0).getOriginalFilename();

            return filename == null || filename.isBlank();
        }
        return false;
    }
}
