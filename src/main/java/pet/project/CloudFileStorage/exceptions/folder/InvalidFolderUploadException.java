package pet.project.CloudFileStorage.exceptions.folder;

public class InvalidFolderUploadException extends FolderRequestException {
    public InvalidFolderUploadException(String message) {
        super(message);
    }

    public InvalidFolderUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
