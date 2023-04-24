package ru.moore.archive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.CommentsCase;
import ru.moore.archive.models.entity.CommentsInventory;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsCaseRepository extends JpaRepository<CommentsCase, Long> {

    Optional<CommentsCase> findByCaseIdAndAuthorIdAndDepartment(Long id, Long id1, String department);

    List<CommentsCase> findAllByCaseId(long id);
}
