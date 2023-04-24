package ru.moore.archive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.moore.archive.models.entity.Inventory;

@Repository
public interface AcquisitionInventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

//    Page<AcquisitionInventory> findByStatusRecordIdIn(List<Long> statusId, Pageable of, Specification<AcquisitionInventory> spec);

//    @Query(value = "select * from ar_inventory where status_id = :status_id", nativeQuery = true)

}
