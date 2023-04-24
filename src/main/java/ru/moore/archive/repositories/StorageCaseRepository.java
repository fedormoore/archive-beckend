package ru.moore.archive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.Case;
import ru.moore.archive.repositories.specifications.StorageCaseSpecifications;

import java.util.List;

@Repository
public interface StorageCaseRepository extends JpaRepository<Case, Long>, JpaSpecificationExecutor<Case> {

    List<Case> findByInventoryId(long id);
}
