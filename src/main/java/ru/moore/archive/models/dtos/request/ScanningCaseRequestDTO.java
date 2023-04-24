package ru.moore.archive.models.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.enums.StatusRecordEnum;
import ru.moore.archive.models.entity.CommentsCase;
import ru.moore.archive.models.entity.CommentsInventory;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
@NoArgsConstructor
public class ScanningCaseRequestDTO {

    @Null(groups = OnCreate.class, message = "Поле 'id' должно быть пустым.")
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'id' не может быть пустым.")
    private Long id;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Статус' не может быть пустым.")
    private StatusRecordEnum statusRecord;

    @NotNull (groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'inventoryId' не может быть пустым.")
    private Long inventoryId;

    private CommentsRequestDTO comment;


    //    Отдел сканирования
    @NotBlank(groups = {OnUpdate.class}, message = "Поле 'Папка со сканами' не может быть пустым.")
    private String folderScan;
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Количество скан листов' не может быть пустым.")
    private Integer countSheetScan;
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Количество PDF' не может быть пустым.")
    private Integer countPdf;
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Количество JPG не может быть пустым.")
    private Integer countJpg;
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Количество документов' не может быть пустым.")
    private Integer countDoc;
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Лист использования' не может быть пустым.")
    private Integer sheetUsage;
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Лист заверитель' не может быть пустым.")
    private Integer sheetWitness;

    public void setFolderScan(String folderScan) {
        String result = folderScan.replaceAll("\\s+", " ");
        this.folderScan = result.trim();
    }
}
