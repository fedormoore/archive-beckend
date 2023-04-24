package ru.moore.archive.models.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.enums.StatusRecordEnum;
import ru.moore.archive.models.entity.CommentsCase;
import ru.moore.archive.models.entity.CommentsInventory;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
@NoArgsConstructor
public class StorageCaseRequestDTO {

    @Null(groups = OnCreate.class, message = "Поле 'id' должно быть пустым.")
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'id' не может быть пустым.")
    private Long id;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Статус' не может быть пустым.")
    private StatusRecordEnum statusRecord;

    @NotNull (groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'inventoryId' не может быть пустым.")
    private Long inventoryId;

    private CommentsRequestDTO comment;

    //    Заведующий хранилищем
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Количество листов' не может быть пустым.")
    private Integer countSheets;

}
