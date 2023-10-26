package pet.project.CloudFileStorage.exceptions.file;

public class InvalidFileDownloadException extends FileRequestException {
    public InvalidFileDownloadException(String message) {
        super(message);
    }

    public InvalidFileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
