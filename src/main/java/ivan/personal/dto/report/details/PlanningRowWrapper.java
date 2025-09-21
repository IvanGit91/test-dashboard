package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Data
public class PlanningRowWrapper {

    private String id;
    private String project;
    private String test;
    private String vfName;
    private String typeTarget;
    private String variant;
    private String revision;
    private String release;
    private String sut;
    private String status;
    private Integer importance;
    private Integer k;
    private String executableVersionName;
    private Long version;
    private boolean permission;
    private String queue;
    private int stepTotal = 0;
    private int stepExecuted = 0;
    private String componentType;
    private String created;

}