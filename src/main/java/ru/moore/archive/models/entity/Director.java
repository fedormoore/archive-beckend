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
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Director {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name="status_id")
    private StatusRecord statusRecord;

    @ManyToOne
    @JoinColumn(name="fond_id")
    private Fond fond;

    @Column(name = "number_inventory")
    private String numberInventory;

    @Column(name = "numbers_case_from")
    private String numbersCaseFrom;

    @Column(name = "numbers_case_to")
    private String numbersCaseTo;

    @ManyToOne
    @JoinColumn(name="inventory_author")
    private Account inventoryAuthor;

    @Column(name = "inventory_income_date")
    private Date inventoryIncomeDate;

    @Column(name = "inventory_outcome_date")
    private Date inventoryOutcomeDate;

    @Column(name = "storage_date")
    private Date storageDate;

    @ManyToOne
    @JoinColumn(name="storage_author")
    private Account storageAuthor;

    @Column(name = "scanning_date")
    private Date scanningDate;

    @ManyToOne
    @JoinColumn(name="scanning_author")
    private Account scanningAuthor;

    @Column(name = "loading_date")
    private Date loadingDate;

    @ManyToOne
    @JoinColumn(name="loading_author")
    private Account loadingAuthor;

}
