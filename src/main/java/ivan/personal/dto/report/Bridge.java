package ivan.personal.dto.report;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Data
public class Bridge {

    private Long id;
    private String ip;
    private String port;
    private String description;

}
