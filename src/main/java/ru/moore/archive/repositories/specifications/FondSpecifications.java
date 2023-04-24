package ru.moore.archive.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.moore.archive.models.entity.Fond;

public class FondSpecifications {

//    private static Specification<Product> priceGreaterOrEqualsThan(int minPrice) {
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
//    }
//
//    private static Specification<Product> priceLesserOrEqualsThan(int maxPrice) {
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
//    }
//
//    private static Specification<Product> titleLike(String titlePart) {
//        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
//    }

    public static Specification<Fond> build(MultiValueMap<String, String> params) {
        Specification<Fond> spec = Specification.where(null);
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
