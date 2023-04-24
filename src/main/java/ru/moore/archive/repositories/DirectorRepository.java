package ru.moore.archive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.Director;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    @Query(value = "select inventory.id, inventory.status_id, inventory.fond_id, inventory.number_inventory, inventory.numbers_case_from, inventory.numbers_case_to, " +
            "his_inventory_income.author as inventory_author, his_inventory_income.date_record as inventory_income_date, " +
            "his_inventory_outcome.date_record as inventory_outcome_date, " +
            "his_storage.date_record as storage_date, his_storage.author as storage_author, " +
            "his_scanning.date_record as scanning_date, his_scanning.author as scanning_author, " +
            "his_loading.date_record as loading_date, his_loading.author as loading_author " +
            "from ar_inventory as inventory " +
            "inner join ar_history_inventor as his_inventory_income on his_inventory_income.id=(select id from ar_history_inventor where status_id=2 or status_id=1 ORDER BY id DESC limit 1) " +
            "left join ar_history_inventor as his_inventory_outcome on his_inventory_outcome.id=(select id from ar_history_inventor where status_id=2 ORDER BY id DESC limit 1) " +
            "left join ar_history_inventor as his_storage on his_storage.id=(select id from ar_history_inventor where status_id=5 ORDER BY id DESC limit 1) " +
            "left join ar_history_inventor as his_scanning on his_scanning.id=(select id from ar_history_inventor where status_id=8 ORDER BY id DESC limit 1) " +
            "left join ar_history_inventor as his_loading on his_loading.id=(select id from ar_history_inventor where status_id=10 ORDER BY id DESC limit 1)",
            nativeQuery = true)
    List<Director> findAll();

    @Query(value = "select inventory.id, inventory.status_id, inventory.fond_id, inventory.number_inventory, inventory.numbers_case_from, inventory.numbers_case_to, " +
            "his_inventory_income.author as inventory_author, his_inventory_income.date_record as inventory_income_date, " +
            "his_inventory_outcome.date_record as inventory_outcome_date, " +
            "his_storage.date_record as storage_date, his_storage.author as storage_author, " +
            "his_scanning.date_record as scanning_date, his_scanning.author as scanning_author, " +
            "his_loading.date_record as loading_date, his_loading.author as loading_author " +
            "from ar_inventory as inventory " +
            "inner join ar_history_inventor as his_inventory_income on his_inventory_income.id=(select id from ar_history_inventor where status_id=2 or status_id=1 ORDER BY id DESC limit 1) " +
            "left join ar_history_inventor as his_inventory_outcome on his_inventory_outcome.id=(select id from ar_history_inventor where status_id=2 ORDER BY id DESC limit 1) " +
            "left join ar_history_inventor as his_storage on his_storage.id=(select id from ar_history_inventor where status_id=5 ORDER BY id DESC limit 1) " +
            "left join ar_history_inventor as his_scanning on his_scanning.id=(select id from ar_history_inventor where status_id=8 ORDER BY id DESC limit 1) " +
            "left join ar_history_inventor as his_loading on his_loading.id=(select id from ar_history_inventor where status_id=10 ORDER BY id DESC limit 1) " +
            "where inventory.id = ?1",
            nativeQuery = true)
    Optional<Director> findById(Long id);
}
