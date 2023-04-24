package ru.moore.archive.models.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.enums.StatusRecordEnum;
import ru.moore.archive.enums.StatusInventoryEnum;
import ru.moore.archive.models.entity.CommentsInventory;
import ru.moore.archive.models.entity.Fond;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcquisitionInventoryRequestDTO {

    @Null(groups = OnCreate.class, message = "Поле 'id' должно быть пустым.")
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'id' не может быть пустым.")
    private Long id;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Статус' не может быть пустым.")
    private StatusRecordEnum statusRecord;

    @NotNull (groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Фонд' не может быть пустым.")
    private Fond fond;

    @NotBlank(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Номер описи' не может быть пустым.")
    private String numberInventory;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Статус описи' не может быть пустым.")
    private StatusInventoryEnum statusInventory;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Номера дел с' не может быть пустым.")
    private Integer numbersCaseFrom;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Номера дел по' не может быть пустым.")
    private Integer numbersCaseTo;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Количество литерных дел' не может быть пустым.")
    private Integer countLitter;

    @NotNull (groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Дата поступления' не может быть пустым.")
    private Date dateIncome;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Начальна дата документа в описи' не может быть пустым.")
    private Date dateFrom;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Конечная дата документа в описи' не может быть пустым.")
    private Date dateTo;

    private CommentsRequestDTO comment;

    public void setNumberInventory(String numberInventory) {
        String result = numberInventory.replaceAll("\\s+", " ");
        this.numberInventory = result.trim();
    }
}
