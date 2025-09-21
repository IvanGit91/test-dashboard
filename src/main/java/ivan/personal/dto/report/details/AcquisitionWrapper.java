package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class AcquisitionWrapper {

    private String key;
    private String value;

}

