package ru.moore.archive.models.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FondRequestDTO {

    @Null(groups = OnCreate.class, message = "Поле 'id' должно быть пустым.")
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'id' не может быть пустым.")
    private Long id;

    @NotBlank(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Номер фонда' не может быть пустым.")
    private String numberFond;

    @NotBlank(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Наименование' не может быть пустым.")
    private String name;

    public void setNumberFond(String numberFond) {
        String result = numberFond.replaceAll("\\s+", " ");
        this.numberFond = result.trim();
    }

    public void setName(String name) {
        String result = name.replaceAll("\\s+", " ");
        this.name = result.trim();
    }
}
