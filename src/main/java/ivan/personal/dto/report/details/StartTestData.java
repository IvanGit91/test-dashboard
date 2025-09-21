package ivan.personal.dto.report.details;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Data
public class StartTestData {

    private ObjectNode message;
    private boolean status;

}
