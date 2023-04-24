package ru.moore.archive.models.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.enums.StatusRecordEnum;
import ru.moore.archive.models.entity.CommentsInventory;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadingInventoryRequestDTO {

    @NotNull(groups = {OnUpdate.class}, message = "Поле 'id' не может быть пустым.")
    private Long id;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Статус' не может быть пустым.")
    private StatusRecordEnum statusRecord;

    @NotEmpty(groups = {OnUpdate.class}, message = "Поле 'Создано карточек дел' не может быть пустым.")
    private Integer createdCardsCase;

    private CommentsRequestDTO comment;

}
