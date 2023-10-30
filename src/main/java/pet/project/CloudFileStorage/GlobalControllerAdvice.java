package pet.project.CloudFileStorage;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pet.project.CloudFileStorage.exceptions.InvalidUserRegistrationException;
import pet.project.CloudFileStorage.exceptions.file.FileOperationException;
import pet.project.CloudFileStorage.exceptions.file.FileRequestException;
import pet.project.CloudFileStorage.exceptions.folder.FolderOperationException;
import pet.project.CloudFileStorage.exceptions.folder.FolderRequestException;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(InvalidUserRegistrationException.class)
    public RedirectView handleInvalidUserRegistrationRequest(InvalidUserRegistrationException e,
                                                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/registration", true);
    }

    @ExceptionHandler(FileRequestException.class)
    public RedirectView handleInvalidFileRequests(FileRequestException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }

    @ExceptionHandler(FolderRequestException.class)
    public RedirectView handleInvalidFileRequests(FolderRequestException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }

    @ExceptionHandler(FileOperationException.class)
    public RedirectView handleInvalidFileRequests(FileOperationException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }

    @ExceptionHandler(FolderOperationException.class)
    public RedirectView handleInvalidFileRequests(FolderOperationException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }

    @ExceptionHandler(Exception.class)
    public RedirectView handleAll(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }
}
