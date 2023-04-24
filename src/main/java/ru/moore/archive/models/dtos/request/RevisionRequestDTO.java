package ru.moore.archive.models.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.models.entity.CommentsInventory;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevisionRequestDTO {

    @NotBlank(message = "Поле 'text' не может быть пустым.")
    private String text;

    private CommentsInventory comment;

    public void setText(String text) {
        String result = text.replaceAll("\\s+", " ");
        this.text = result.trim();
    }
}