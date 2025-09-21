package ivan.personal.dto.report;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@Data
public class StatReportReq implements InitializingBean {

    private String name;
    private Integer total = 0;
    private List<TestRowWrapper> pass;
    private List<TestRowWrapper> fail;
    private List<TestRowWrapper> running;
    private List<TestRowWrapper> noRun;
    private List<Long> tests;

    @Override
    public void afterPropertiesSet() {
        this.tests = new ArrayList<>();
    }
}
