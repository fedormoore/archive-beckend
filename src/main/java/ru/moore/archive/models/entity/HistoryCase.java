package ru.moore.archive.models.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ar_history_case")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_record")
    private Date dateRecord;

    @ManyToOne()
    @JoinColumn(name="status_id")
    private StatusRecord statusRecord;

    @Column(name = "text")
    private String text;

    @ManyToOne()
    @JoinColumn(name="author")
    private Account author;

    @Column(name = "case_id")
    private Long caseId;

}