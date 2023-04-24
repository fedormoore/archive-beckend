package ru.moore.archive.models.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ar_status_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="status")
    private String status;

    @Column(name = "visible")
    private String visible;

    @Column(name = "selection")
    private String selection;

}