package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class GlobalHarInfos {

    private int total_http_requests;
    private int total_upload_bytes;
    private int total_download_bytes;
    private int total_time;

}
