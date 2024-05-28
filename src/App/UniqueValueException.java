package App;

public class UniqueValueException extends Exception {
    public UniqueValueException(String message) {
        super(message);
    }

    public UniqueValueException(String message, Throwable cause) {
        super("UniqueValueException: " + message, cause);
    }
}

