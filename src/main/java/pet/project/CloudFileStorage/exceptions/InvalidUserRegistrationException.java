package pet.project.CloudFileStorage.exceptions;

public class InvalidUserRegistrationException extends RuntimeException {
    public InvalidUserRegistrationException(String message) {
        super(message);
    }

    public InvalidUserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
