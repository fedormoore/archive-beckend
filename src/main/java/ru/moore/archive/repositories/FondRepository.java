package ru.moore.archive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.Fond;

import java.util.Optional;

@Repository
public interface FondRepository extends JpaRepository<Fond, Long>, JpaSpecificationExecutor<Fond> {

    Optional<Fond> findByName(String name);

    Optional<Fond> findByNameAndIdIsNot(String name, Long id);

    Optional<Fond> findByNumberFond(String number);

    Optional<Fond> findByNumberFondAndIdIsNot(String number, Long id);
}
