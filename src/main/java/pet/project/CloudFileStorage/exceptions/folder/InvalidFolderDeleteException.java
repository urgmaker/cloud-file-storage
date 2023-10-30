package pet.project.CloudFileStorage.exceptions.folder;

public class InvalidFolderDeleteException extends FolderRequestException {
    public InvalidFolderDeleteException(String message) {
        super(message);
    }

    public InvalidFolderDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
