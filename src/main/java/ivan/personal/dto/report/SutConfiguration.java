package ivan.personal.dto.report;

import ivan.personal.dto.report.details.PlanningRowWrapper;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope("prototype")
@Data
public class SutConfiguration {

    private Long id;
    private String sutId;
    private String sutName;
    private String componentType;
    private String timezone;
    private Integer queueNightEnd;
    private Integer queueNightInit;
    private Integer queueDayEnd;
    private Integer queueDayInit;
    private String weekend;
    private Bridge bridge;
    private boolean stop = false;
    private String reason;
    private String details;
    private boolean reserved = true;
    private boolean connected = true;
    private List<DutElement> duts;
    private String labelDayNight;
    private List<PlanningRowWrapper> planningList;

}
