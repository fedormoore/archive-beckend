package ru.moore.archive.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.dtos.response.CaseResponseDTO;
import ru.moore.archive.models.entity.Case;
import ru.moore.archive.repositories.specifications.AcquisitionCaseSpecifications;

import java.util.List;

@Repository
public interface AcquisitionCaseRepository extends JpaRepository<Case, Long>, JpaSpecificationExecutor<Case> {

}
