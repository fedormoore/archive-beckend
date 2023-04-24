package ru.moore.archive.models.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ar_comments_inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsInventory {

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

    @Column(name = "inventory_id")
    private Long inventoryId;

}