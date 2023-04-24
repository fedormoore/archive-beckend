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
@Table(name = "ar_case")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Case extends BaseEntity {

    @ManyToOne()
    @JoinColumn(name="status_id")
    private StatusRecord statusRecord;

    @Transient
    private CommentsCase comment;

    @Column(name = "inventory_id", insertable = false, updatable = false)
    private Long inventoryId;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory acquisitionInventory;

//    Отдел комплектования

    @Column(name = "date_income")
    private Date dateIncome;

    @Column(name = "numbers_case")
    private Integer numbersCase;

    @Column(name = "numbers_storage")
    private Integer numberStorage;

//    Заведующий хранилищем

    @Column(name = "count_sheets")
    private Integer countSheets;

//    Отдел сканирования

    @Column(name = "folder_scan")
    private String folderScan;

    @Column(name = "count_sheet_scan")
    private Integer countSheetScan;

    @Column(name = "count_pdf")
    private Integer countPdf;

    @Column(name = "count_jpg")
    private Integer countJpg;

    @Column(name = "count_doc")
    private Integer countDoc;

    @Column(name = "sheet_usage")
    private Integer sheetUsage;

    @Column(name = "sheet_witness")
    private Integer sheetWitness;

//    Отдел загрузки

    @Column(name = "created_cards_case")
    private Integer createdCardsCase;

    @Column(name = "load_sheet")
    private Integer loadSheet;

    @Column(name = "load_file")
    private Integer loadFile;

}
