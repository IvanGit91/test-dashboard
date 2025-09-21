package ivan.personal.dto.report;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
@Data
public class TestRepeat {

    private ReportStatus reportStatus;
    private Date created;
    private String hilNotes;
    private String valNotes;
    private String elapsedTime;
    private String opnReason;
    private Map<String, List<Long>> statistics;

}
