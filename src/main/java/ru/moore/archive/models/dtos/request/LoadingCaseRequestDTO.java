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
public class LoadingCaseRequestDTO {

    @Null(groups = OnCreate.class, message = "Поле 'id' должно быть пустым.")
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'id' не может быть пустым.")
    private Long id;

    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Статус' не может быть пустым.")
    private StatusRecordEnum statusRecord;

    @NotNull (groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'inventoryId' не может быть пустым.")
    private Long inventoryId;

    private CommentsRequestDTO comment;

    //    Отдел загрузки
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Создано карточек дел' не может быть пустым.")
    private Integer createdCardsCase;
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Загруженно листов' не может быть пустым.")
    private Integer loadSheet;
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'Загруженно файлов' не может быть пустым.")
    private Integer loadFile;

}
