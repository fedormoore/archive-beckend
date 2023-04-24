package ru.moore.archive.models.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FondResponseDTO {

    private Long id;
    private String numberFond;
    private String name;

}
