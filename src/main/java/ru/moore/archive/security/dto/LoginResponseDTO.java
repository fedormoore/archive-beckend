package ru.moore.archive.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.moore.archive.models.dtos.response.RoleDTO;
import ru.moore.archive.models.entity.Role;

import java.util.List;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {

    private final String type = "Bearer";
    private final String accessToken;
    private final String refreshToken;
    private final List<RoleDTO> roles;
}
