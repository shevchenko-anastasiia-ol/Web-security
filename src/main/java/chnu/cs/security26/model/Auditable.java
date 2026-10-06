package chnu.cs.security26.model;

/*
    @author     owner
    @project    security26
    @class      Auditable
    @version    1.0.0
    @since      06.10.2026 - 15:06.06
*/

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class Auditable {
    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String lastModifiedBy;
    @Version
    private Long version;

}
