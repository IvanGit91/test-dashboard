package ivan.personal.dto.report.details;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
@Scope("prototype")
@Data
public class ReportKeywordWrapper {

    private Long id;
    private String name;
    private Boolean devTool = false;
    private String sut;
    private Boolean isEnabled;
    private LinkedHashMap<String, ParameterWrapper> parameters;
    private List<AcquisitionWrapper> acquisition;
    private ReportDetails subTest;
    private int position;
    private ObjectNode feedback;
    private LinkedHashMap<String, ReportSceneWrapper> sceneList;

}
