package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class ParameterWrapper {
    public static final String TYPE_INPUT_STRING = "text";
    public static final String TYPE_INPUT_INTEGER = "integer";
    public static final String TYPE_INPUT_REAL = "real";
    public static final String TYPE_INPUT_SELECT = "select";

    private String type;
    private String name;
    private String placeholder;
    private Object value;
    private String title;
    private boolean father;
    private boolean estimable;
    private boolean containsModel;
    private boolean visible = true;
    private boolean isOutput = false;
    private boolean multiple = false;
}

