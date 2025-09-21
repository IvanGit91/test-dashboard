package ivan.personal.dto.report;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Data
public class Report {

    private Long id;
    private Long vfId;
    private String status;
    private String name;
    private Long version;
    private String vfName;
    private String typeTarget;
    private String variant;
    private String revision;
    private String release;
    private String tag;
    private String tc;
    private Integer imp;
    private String time;
    private String created;
    private String maker;
    private String launcher;
    private String sut;
    private String reason;
    private String opn;
    private String opStatus;
    private String valNotes;
    private String opnReason;
    private Long projectId;
    private String idPlanning;
    private String scheduling;

}
