package ru.moore.archive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.StatusInventory;

@Repository
public interface StatusInventoryRepository extends JpaRepository<StatusInventory, Long> {

}
