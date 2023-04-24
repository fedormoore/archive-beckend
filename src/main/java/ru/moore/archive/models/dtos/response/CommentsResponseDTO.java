package ru.moore.archive.models.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentsResponseDTO {

    private String text;
    private AuthorResponseDTO author;
    private String department;

}
