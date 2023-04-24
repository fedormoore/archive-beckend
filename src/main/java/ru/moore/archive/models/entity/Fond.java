package ru.moore.archive.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Entity
@Table(name = "ar_fond")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Fond extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "number_fond")
    private String numberFond;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author")
    private Account author;

}
