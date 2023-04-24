package ru.moore.archive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.StatusRecord;

import java.util.List;

@Repository
public interface StatusRecordRepository extends JpaRepository<StatusRecord, Long> {

    List<StatusRecord> findAllBySelectionContains(String typeInterface);


}
