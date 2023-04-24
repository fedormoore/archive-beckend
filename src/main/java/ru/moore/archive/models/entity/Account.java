package ru.moore.archive.models.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ar_accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Null(groups = OnCreate.class, message = "Поле 'id' должно быть пустым.")
    @NotNull(groups = {OnUpdate.class}, message = "Поле 'id' не может быть пустым.")
    private Long id;

    @Column(name = "login")
    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Логин' не может быть пустым.")
    private String login;

    @Column(name="password")
    @Null(groups = {OnUpdate.class}, message = "Поле 'Пароль' должно быть пустым.")
    @NotEmpty(groups = OnCreate.class, message = "Поле 'Пароль' не может быть пустым.")
    private String password;

    @Column(name = "last_name")
    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Фамилия' не может быть пустым.")
    private String lastName;

    @Column(name = "first_name")
    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Имя' не может быть пустым.")
    private String firstName;

    @Column(name = "middle_names")
    private String middleNames;

    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "У пользователя должна быть роль.")
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="ar_users_roles",
            joinColumns= {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private List<Role> roles;

//    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
//    private List<Inventory> inventories;

    @Column(name = "created_at", updatable=false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getId().equals(account.getId()) &&
                getLogin().equals(account.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getLastName(), getFirstName(), getMiddleNames(), getRoles());
    }
}