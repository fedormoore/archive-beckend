package ru.moore.archive.models.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.enums.StatusInventoryConverter;

@Data
@NoArgsConstructor
public class StatusInventoryResponseDTO {

    private Long id;
    private String status;
    private String statusEnum;

    public String getStatusEnum() {
        StatusInventoryConverter statusConverter = new StatusInventoryConverter();
        return statusConverter.convertToEntityAttribute(status).name();
    }
}
