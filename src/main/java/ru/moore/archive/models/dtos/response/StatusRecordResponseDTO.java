package ru.moore.archive.models.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.enums.StatusRecordConverter;

@Data
@NoArgsConstructor
public class StatusRecordResponseDTO {

    private Long id;
    private String status;
    private String statusEnum;

    public String getStatusEnum() {
        StatusRecordConverter statusConverter = new StatusRecordConverter();
        return statusConverter.convertToEntityAttribute(status).name();
    }
}
