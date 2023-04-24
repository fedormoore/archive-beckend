package ru.moore.archive.models.dtos.response;

import lombok.*;
import ru.moore.archive.models.entity.StatusRecord;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DirectorResponseDTO {

    private Long id;
    private FondResponseDTO fond;
    private String numberInventory;
    private String numbersCaseFrom;
    private String numbersCaseTo;

    private AuthorResponseDTO inventoryAuthor;
    private Date inventoryIncomeDate;
    private Date inventoryOutcomeDate;

    private Date storageDate;
    private AuthorResponseDTO storageAuthor;

    private Date scanningDate;
    private AuthorResponseDTO scanningAuthor;

    private Date loadingDate;
    private AuthorResponseDTO loadingAuthor;

    private StatusRecord statusRecord;

    public String getStatusRecord() {
        return statusRecord.getStatus();
    }


}
