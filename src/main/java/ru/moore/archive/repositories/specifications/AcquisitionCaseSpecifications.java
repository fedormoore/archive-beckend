package ru.moore.archive.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.moore.archive.models.entity.Case;

public class AcquisitionCaseSpecifications {


    public static Specification<Case> whereInventoryId(String inventoryId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("inventoryId"), inventoryId);
    }

    public static Specification<Case> build(MultiValueMap<String, String> params) {
        Specification<Case> spec = Specification.where(null);
        if (params.containsKey("inventoryId") && !params.getFirst("inventoryId").isBlank()) {
            spec = spec.and(whereInventoryId(params.getFirst("inventoryId")));
        }
        return spec;
    }

}
