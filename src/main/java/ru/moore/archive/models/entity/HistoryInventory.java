package ru.moore.archive.models.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ar_history_inventor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryInventory {

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

    @Column(name = "inventory_id")
    private Long inventoryId;

}