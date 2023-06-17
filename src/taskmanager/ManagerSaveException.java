package taskmanager;

import java.io.IOException;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(final String message) {
        super(message);
    }

    public ManagerSaveException(String message, IOException e) {
        super(message);
    }

    public ManagerSaveException(String message, NullPointerException exp) {

        super(message);
    }
}