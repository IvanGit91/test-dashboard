package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;


@Component
@Scope("prototype")
@Data
public class ReportTestRepeatWrapper {

    private LinkedList<ReportRoutineWrapper> routines;

}

