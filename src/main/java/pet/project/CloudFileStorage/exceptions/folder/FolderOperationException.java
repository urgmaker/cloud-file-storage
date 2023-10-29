package pet.project.CloudFileStorage.exceptions.folder;

public class FolderOperationException extends RuntimeException {
    public FolderOperationException(String message) {
        super(message);
    }

    public FolderOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
