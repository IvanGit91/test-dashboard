package ivan.personal.dto.report;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Data
public class DutElement {

    private Long id;
    private String sutId;
    private String sutName;
    private String componentType;
    private Integer index;
    private SutConfiguration sut;
    private boolean connected = true;
    private boolean reserved = true;

}
