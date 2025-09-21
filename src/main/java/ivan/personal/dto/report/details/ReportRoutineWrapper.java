package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
@Scope("prototype")
@Data
public class ReportRoutineWrapper {

    private Long id;
    private String name;
    private Integer repeat;
    private Integer repeatCount;
    private Boolean exitOnFail;
    private String type;
    private LinkedList<ReportRoutineRepeatWrapper> repeatList;

}

