package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Data
public class StartTestMessageWrapper {

    private String testId;
    private String message;
    private StartTestData data;

}
