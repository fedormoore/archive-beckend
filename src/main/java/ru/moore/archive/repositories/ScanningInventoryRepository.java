package ru.moore.archive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.Inventory;

@Repository
public interface ScanningInventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

//    List<Inventory> findByStatusIn(String[] status);

//    Page<ScanningInventory> findByStatusIn(Pageable pageable, String[] status);

}
