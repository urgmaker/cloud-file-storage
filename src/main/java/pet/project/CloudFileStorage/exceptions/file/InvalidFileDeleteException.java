package pet.project.CloudFileStorage.exceptions.file;

public class InvalidFileDeleteException extends FileRequestException {
    public InvalidFileDeleteException(String message) {
        super(message);
    }

    public InvalidFileDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
