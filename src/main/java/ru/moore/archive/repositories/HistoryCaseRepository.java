package ru.moore.archive.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.HistoryCase;
import ru.moore.archive.models.entity.HistoryInventory;

import java.util.List;

@Repository
public interface HistoryCaseRepository extends JpaRepository<HistoryCase, Long> {

    List<HistoryCase> findAllByCaseId(long id, Sort sort);
}
