package chnu.cs.security26.model;

import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document
public class Book extends Auditable {
    @Id
    private String id;
    private String title;
    private String author;
    private String description;

    public Book(String title, String author, String description){
        this.title = title;
        this.author = author;
        this.description = description;
    }
}
