package ru.moore.archive.models.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.enums.StatusRecordEnum;
import ru.moore.archive.models.entity.CommentsInventory;
import ru.moore.archive.models.entity.Fond;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanningInventoryRequestDTO {

    @NotNull(groups = {OnUpdate.class}, message = "Поле 'id' не может быть пустым.")
    private Long id;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Статус' не может быть пустым.")
    private StatusRecordEnum statusRecord;

    @NotBlank(groups = {OnUpdate.class}, message = "Поле 'Папка со сканами' не может быть пустым.")
    private String folderScan;

    @NotEmpty(groups = {OnUpdate.class}, message = "Поле 'Количество скан листов' не может быть пустым.")
    private Integer countSheetScan;

    @NotEmpty(groups = {OnUpdate.class}, message = "Поле 'Количество PDF' не может быть пустым.")
    private Integer countPdf;

    @NotEmpty(groups = {OnUpdate.class}, message = "Поле 'Количество JPG' не может быть пустым.")
    private Integer countJpg;

    private CommentsRequestDTO comment;

    public void setFolderScan(String folderScan) {
        String result = folderScan.replaceAll("\\s+", " ");
        this.folderScan = result.trim();
    }
}
