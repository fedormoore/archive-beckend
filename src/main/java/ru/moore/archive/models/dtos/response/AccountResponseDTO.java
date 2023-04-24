package ru.moore.archive.models.dtos.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.archive.models.entity.Role;

import java.util.List;

@Data
@NoArgsConstructor
public class AccountResponseDTO {

    private Long id;
    private String login;
    private String lastName;
    private String firstName;
    private String middleNames;
    private List<Role> roles;

}
