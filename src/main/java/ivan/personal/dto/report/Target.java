package ivan.personal.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Scope("prototype")
@Data
public class Target {

    private Long id;
    private String type;
    private String name;
    private String variant;
    private String revision;
    private String release;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z", timezone = "Europe/Rome")
    private Date created;
    private String description;

    public String getCreateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.created);
    }
}
