package pet.project.CloudFileStorage.exceptions.folder;

public class InvalidFolderRenameException extends FolderRequestException {
    public InvalidFolderRenameException(String message) {
        super(message);
    }

    public InvalidFolderRenameException(String message, Throwable cause) {
        super(message, cause);
    }
}
