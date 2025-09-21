package ivan.personal.dto.report.details;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@Scope("prototype")
@Data
public class ReportSceneRepeatWrapper {

    private LinkedHashMap<String, ReportActionWrapper> actions;
    private ObjectNode feedback;

}
