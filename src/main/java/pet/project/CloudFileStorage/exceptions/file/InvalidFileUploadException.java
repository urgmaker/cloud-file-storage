package pet.project.CloudFileStorage.exceptions.file;

public class InvalidFileUploadException extends FileRequestException{
    public InvalidFileUploadException(String message) {
        super(message);
    }

    public InvalidFileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
