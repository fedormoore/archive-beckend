package ru.moore.archive.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.moore.archive.enums.StatusRecordEnum;
import ru.moore.archive.models.entity.Inventory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AcquisitionInventorySpecifications {

    //    private static Specification<Product> priceGreaterOrEqualsThan(int minPrice) {
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
//    }
//
//    private static Specification<Product> priceLesserOrEqualsThan(int maxPrice) {
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
//    }
//
//    public static Specification<Inventory> whereStatus(String status) {
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
//    }

    public static Specification<Inventory> whereStatusId(Long statusId) {
        return (Root<Inventory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("statusRecord"), statusId);
        };
    }

    public static Specification<Inventory> build(MultiValueMap<String, String> params) {
        Specification<Inventory> spec = Specification.where(null);

        for (StatusRecordEnum statusEnum: StatusRecordEnum.values()) {
            if (statusEnum.getVisible().contains("ACQUISITION")) {
                spec = spec.or(whereStatusId(statusEnum.getId()));
            }
        }

//        if (params.containsKey("min_price") && !params.getFirst("min_price").isBlank()) {
//            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(Integer.parseInt(params.getFirst("min_price"))));
//        }
//        if (params.containsKey("max_price") && !params.getFirst("max_price").isBlank()) {
//            spec = spec.and(ProductSpecifications.priceLesserOrEqualsThan(Integer.parseInt(params.getFirst("max_price"))));
//        }
//        if (params.containsKey("title") && !params.getFirst("title").isBlank()) {
//            spec = spec.and(ProductSpecifications.titleLike(params.getFirst("title")));
//        }
        return spec;
    }

}
