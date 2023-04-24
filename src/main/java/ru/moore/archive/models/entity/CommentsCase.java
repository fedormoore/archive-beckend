package ru.moore.archive.models.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ar_comments_case")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne()
    @JoinColumn(name="author")
    private Account author;

    @Column(name = "department")
    private String department;

    @Column(name = "case_id")
    private Long caseId;

}