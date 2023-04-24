package ru.moore.archive.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.HistoryInventory;

import java.util.List;

@Repository
public interface HistoryInventoryRepository extends JpaRepository<HistoryInventory, Long> {

    List<HistoryInventory> findAllByInventoryId(long id, Sort sort);
}
