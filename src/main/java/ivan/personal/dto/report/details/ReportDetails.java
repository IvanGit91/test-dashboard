package ivan.personal.dto.report.details;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReportDetails {

    private Long vfId;
    private String testID;
    private String name;
    private Integer priority;
    private Long version;
    private String description;
    private Long maker;
    private Long estimated;
    private Long duration;
    private StartTestMessageWrapper feedbackTestInitialization;
    private Object feedbackEndTest;
    private List<ReportTestRepeatWrapper> reportTestRepeatWrappers;
    private List<ReportFile> reportFileList;
    private Map<String, VariableWrapperExecutableTest> variables;
    private HarInfo harInfo;

}
