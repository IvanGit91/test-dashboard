package ivan.personal.dto.report.details;

import ivan.personal.dto.report.Report;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class ReportFile {

    private Long id;
    private Report report;
    private FileEntity file;

}
