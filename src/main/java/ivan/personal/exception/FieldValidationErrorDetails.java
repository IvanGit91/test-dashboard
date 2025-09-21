package ivan.personal.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FieldValidationErrorDetails {
    private String errorTitle;
    private int errorStatus;
    private String errorDetail;
    private long errorTimestamp;
    private String errorPath;
    private String errorDeveloperMessage;
    private Map<String, List<FieldValidationError>> errors = new HashMap<>();

}
