package ivan.personal.dto.report.details;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Data
public class FileEntity {

    private Long id;
    private String type;
    private String description;
    private String name;
    private String filename;
    private String importExportFile;

}
