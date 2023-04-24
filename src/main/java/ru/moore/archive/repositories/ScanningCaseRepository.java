package ru.moore.archive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.Case;

@Repository
public interface ScanningCaseRepository extends JpaRepository<Case, Long>, JpaSpecificationExecutor<Case> {

}
