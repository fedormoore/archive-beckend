package ru.moore.archive.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.moore.archive.enums.StatusRecordEnum;
import ru.moore.archive.models.entity.Case;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class LoadingCaseSpecifications {

    public static Specification<Case> whereStatusId(Long statusId) {
        return (Root<Case> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("statusRecord"), statusId);
        };
    }


    public static Specification<Case> build(MultiValueMap<String, String> params) {
        Specification<Case> spec = Specification.where(null);

        for (StatusRecordEnum statusEnum: StatusRecordEnum.values()) {
            if (statusEnum.getVisible().contains("LOADING")){
                spec = spec.or(whereStatusId(statusEnum.getId()));
            }
        }

        return spec;
    }

}
