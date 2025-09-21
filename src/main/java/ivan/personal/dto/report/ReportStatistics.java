package ivan.personal.dto.report;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@Scope("prototype")
@Data
public class ReportStatistics {

    private int totalTest;
    private Collection<StatReportReq> clusters;
    private Map<String, Integer> reportGroup;

}
