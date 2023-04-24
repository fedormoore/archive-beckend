package ru.moore.archive.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ar_inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Inventory extends BaseEntity {

    @ManyToOne()
    @JoinColumn(name="status_id")
    private StatusRecord statusRecord;

    @Transient
    private CommentsInventory comment;

    @OneToMany(mappedBy = "acquisitionInventory", fetch = FetchType.LAZY)
    private List<Case> caseList;

//    Отдел комплектования

    @ManyToOne
    @JoinColumn(name = "fond_id")
    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Фонд' не может быть пустым.")
    private Fond fond;

    @Column(name = "number_inventory")
    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Номер описи' не может быть пустым.")
    private String numberInventory;

    @ManyToOne()
    @JoinColumn(name="status_inventory_id")
    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Статус описи' не может быть пустым.")
    private StatusInventory statusInventory;

    @Column(name = "date_income")
    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Дата поступления' не может быть пустым.")
    private Date dateIncome;

    @Column(name = "numbers_case_from")
    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Номера дел с' не может быть пустым.")
    private Integer numbersCaseFrom;

    @Column(name = "numbers_case_to")
    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Номера дел по' не может быть пустым.")
    private Integer numbersCaseTo;

    @Column(name = "count_litter")
    @NotEmpty(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Количество литерных дел' не может быть пустым.")
    private Integer countLitter;

    @Column(name = "date_from")
    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Начальна дата документа в описи' не может быть пустым.")
    private Date dateFrom;

    @Column(name = "date_to")
    @NotNull(groups = {OnUpdate.class, OnCreate.class}, message = "Поле 'Конечная дата документа в описи' не может быть пустым.")
    private Date dateTo;

//    Заведующий хранилищем

    @Column(name = "count_sheets")
    @NotEmpty(groups = {OnUpdate.class}, message = "Поле 'Количество листов' не может быть пустым.")
    private Integer countSheets;

//    Отдел сканирования

    @Column(name = "folder_scan")
    @NotEmpty(groups = {OnUpdate.class}, message = "Поле 'Папка со сканами' не может быть пустым.")
    private String folderScan;

    @Column(name = "count_sheet_scan")
    @NotEmpty(groups = {OnUpdate.class}, message = "Поле 'Количество скан листов' не может быть пустым.")
    private Integer countSheetScan;

    @Column(name = "count_pdf")
    @NotEmpty(groups = {OnUpdate.class}, message = "Поле 'Количество PDF' не может быть пустым.")
    private Integer countPdf;

    @Column(name = "count_jpg")
    @NotEmpty(groups = {OnUpdate.class}, message = "Поле 'Количество JPG' не может быть пустым.")
    private Integer countJpg;

//    Отдел загрузки

    @Column(name = "created_cards_case")
    @NotEmpty(groups = {OnUpdate.class}, message = "Поле 'Создано карточек дел' не может быть пустым.")
    private Integer createdCardsCase;

//

}
