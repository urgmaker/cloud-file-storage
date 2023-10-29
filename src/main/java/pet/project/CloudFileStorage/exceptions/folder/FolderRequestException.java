package pet.project.CloudFileStorage.exceptions.folder;

public abstract class FolderRequestException extends RuntimeException {
    public FolderRequestException(String message) {
        super(message);
    }

    public FolderRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
