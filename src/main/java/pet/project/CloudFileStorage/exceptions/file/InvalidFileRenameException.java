package pet.project.CloudFileStorage.exceptions.file;

public class InvalidFileRenameException extends FileRequestException{
    public InvalidFileRenameException(String message) {
        super(message);
    }

    public InvalidFileRenameException(String message, Throwable cause) {
        super(message, cause);
    }
}
