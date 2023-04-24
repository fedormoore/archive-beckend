package ru.moore.archive.security.dto;

import lombok.*;
import ru.moore.archive.models.entity.Account;
import ru.moore.archive.models.entity.OnCreate;
import ru.moore.archive.models.entity.OnUpdate;
import ru.moore.archive.models.entity.Role;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SecurityAccountDTO implements Serializable {

//    private Long id;
//    private String login;
//    private Account account;
//    private List<Role> roles;

    private Long id;
    private String login;
    private String lastName;
    private String firstName;
    private String middleNames;
    private List<Role> roles;

}
