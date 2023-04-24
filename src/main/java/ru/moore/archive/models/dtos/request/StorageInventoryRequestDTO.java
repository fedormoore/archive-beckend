package ru.moore.archive.models.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.enums.StatusRecordConverter;
import ru.moore.archive.enums.StatusRecordEnum;
import ru.moore.archive.models.entity.CommentsInventory;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;
import ru.moore.archive.models.entity.StatusRecord;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageInventoryRequestDTO {

    @NotNull(groups = {OnUpdate.class}, message = "Поле 'id' не может быть пустым.")
    private Long id;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Статус' не может быть пустым.")
    private StatusRecordEnum statusRecord;

    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Количество листов' не может быть пустым.")
    private Integer countSheets;

    private CommentsRequestDTO comment;

}
