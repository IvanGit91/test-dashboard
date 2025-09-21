package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Scope("prototype")
@Data
public class HarInfo {

    private HarStats harStats;
    private HashMap<String, DetailsHttpInfo> httpResponseCounter;
    private GlobalHarInfos globalHarInfos;

}
