package ru.moore.archive.models.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.models.entity.Account;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
public class CommentsRequestDTO {

    private String text;

    public void setText(String text) {
        String result = text.replaceAll("\\s+", " ");
        this.text = result.trim();
    }
}
