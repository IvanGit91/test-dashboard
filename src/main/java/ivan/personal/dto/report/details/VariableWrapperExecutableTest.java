package ivan.personal.dto.report.details;

import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class VariableWrapperExecutableTest {

    private String type;
    private ArrayNode values;

}
