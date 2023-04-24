package ru.moore.archive.models.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.models.entity.Role;
import ru.moore.archive.models.entity.StatusRecord;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class HistoryResponseDTO {

    private Long id;
    private Date dateRecord;
    private StatusRecord statusRecord;
    private String text;
    private AuthorResponseDTO author;

    public String getStatusRecord() {
        return statusRecord.getStatus();
    }
}
