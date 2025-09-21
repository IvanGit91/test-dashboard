package ivan.personal.dto.report.details;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@Scope("prototype")
@Data
public class ReportRoutineRepeatWrapper {

    private LinkedHashMap<String, ReportKeywordWrapper> keywords;
    private ObjectNode feedback;

}

