package ivan.personal.exception;

import lombok.Data;

@Data
public class FieldValidationError {
    private String filed;
    private String message;
    private MessageType type;

    public enum MessageType {
        SUCCESS, INFO, WARNING, ERROR
    }
}
