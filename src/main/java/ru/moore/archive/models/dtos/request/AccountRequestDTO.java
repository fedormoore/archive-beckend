package ru.moore.archive.models.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;
import ru.moore.archive.models.entity.Role;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDTO {

    @Null(groups = OnCreate.class, message = "Поле 'id' должно быть пустым.")
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'id' не может быть пустым.")
    private Long id;

    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Логин' не может быть пустым.")
    private String login;

    @Null(groups = {OnUpdate.class}, message = "Поле 'Пароль' должно быть пустым.")
    @NotEmpty(groups = OnCreate.class, message = "Поле 'Пароль' не может быть пустым.")
    private String password;

    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Фамилия' не может быть пустым.")
    private String lastName;

    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Имя' не может быть пустым.")
    private String firstName;

    private String middleNames;

    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "У пользователя должна быть роль.")
    private List<Role> roles;

}