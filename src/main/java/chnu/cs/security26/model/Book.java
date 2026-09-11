package chnu.cs.security26.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Book {
    private String id;
    private String title;
    private String author;
    private String description;
}
