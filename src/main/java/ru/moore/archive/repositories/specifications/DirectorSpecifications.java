package ru.moore.archive.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.moore.archive.models.entity.Director;

public class DirectorSpecifications {

    public static Specification<Director> whereStatus(String status) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("status"), String.format("%%%s%%", status));
    }

    public static Specification<Director> build(MultiValueMap<String, String> params) {
        Specification<Director> spec = Specification.where(null);
        if (params.containsKey("status") && !params.get("status").isEmpty()) {
            for(String word : params.get("status")) {
                spec = spec.or(whereStatus(word));
            }
        }
        return spec;
    }

}
