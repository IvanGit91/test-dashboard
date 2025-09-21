package ivan.personal.component;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class ApiResponse {

    private boolean status;
    private String message;
    private Object data;

    public void setResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
