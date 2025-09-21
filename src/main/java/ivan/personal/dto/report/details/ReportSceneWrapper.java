package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@Scope("prototype")
@Data
public class ReportSceneWrapper {

    private String name;
    private LinkedHashMap<String, ReportSceneRepeatWrapper> repeatList;
}
