package ru.moore.archive.security.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String login;
    private String password;

}
