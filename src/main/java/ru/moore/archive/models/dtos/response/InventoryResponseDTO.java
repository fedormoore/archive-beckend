package ru.moore.archive.models.dtos.response;

import lombok.*;
import ru.moore.archive.models.entity.StatusRecord;
import ru.moore.archive.models.entity.StatusInventory;

import java.util.Date;

@Data
@NoArgsConstructor
public class InventoryResponseDTO {

    private Long id;
    private StatusRecord statusRecord;
    private CommentResponseDTO comment;

    //    Отдел комплектования
    private FondResponseDTO fond;
    private String numberInventory;
    private StatusInventory statusInventory;
    private Date dateIncome;
    private Integer numbersCaseFrom;
    private Integer numbersCaseTo;
    private Integer countLitter;
    private Date dateFrom;
    private Date dateTo;

    //    Заведующий хранилищем
    private String countSheets;

    //    Отдел сканирования
    private String folderScan;
    private String countSheetScan;
    private String countPdf;
    private String countJpg;

    //    Отдел загрузки
    private String createdCardsCase;

    public String getStatusRecord() {
        return statusRecord.getStatus();
    }

    public String getStatusInventory() {
        return statusInventory.getStatus();
    }
}
