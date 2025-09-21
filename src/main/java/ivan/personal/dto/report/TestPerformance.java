package ivan.personal.dto.report;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
@Data
public class TestPerformance {

    private Long id;
    private String testName;
    private Long version;
    private Target target;
    private String uuid;
    private Long delay;
    private String sutName;
    private String sutId;
    private List<TestRepeat> testsRepeat;
}

