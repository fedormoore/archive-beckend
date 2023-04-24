package ru.moore.archive.models.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.models.entity.CommentsInventory;
import ru.moore.archive.models.entity.StatusRecord;

import java.util.Date;

@Data
@NoArgsConstructor
public class CaseResponseDTO {

    private Long id;
    private Long inventoryId;
    private StatusRecord statusRecord;
    private CommentResponseDTO comment;

    //    Отдел комплектования
    private Date dateIncome;
    private Integer numbersCase;
    private Integer numberStorage;

    //    Заведующий хранилищем
    private String countSheets;

    //    Отдел сканирования
    private String folderScan;
    private String countSheetScan;
    private String countPdf;
    private String countJpg;
    private Integer countDoc;
    private Integer sheetUsage;
    private Integer sheetWitness;

    //    Отдел загрузки
    private String createdCardsCase;
    private Integer loadSheet;
    private Integer loadFile;

    public String getStatusRecord() {
        return statusRecord.getStatus();
    }
}
