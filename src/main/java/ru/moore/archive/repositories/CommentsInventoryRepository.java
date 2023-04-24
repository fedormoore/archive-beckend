package ru.moore.archive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.CommentsInventory;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsInventoryRepository extends JpaRepository<CommentsInventory, Long> {

    Optional<CommentsInventory> findByInventoryIdAndAuthorIdAndDepartment(Long id, Long id1, String department);

    List<CommentsInventory> findAllByInventoryId(long id);
}
