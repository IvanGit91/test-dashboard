package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class HarStats {

    private int totalBlocked = 0;
    private int totalDns = 0;
    private int totalSsl = 0;
    private int totalConnect = 0;
    private int totalSend = 0;
    private int totalWait = 0;
    private int totalReceive = 0;

}