package com.test.emailservice.modules.email.sepecifications;

import com.test.emailservice.modules.email.entities.VendorEmail;
import com.test.emailservice.modules.users.entities.User;
import com.test.emailservice.modules.users.resources.UserListRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class VendorEmailSpecification {
    public static Specification<VendorEmail> filter(String filterText) {
        return new Specification<VendorEmail>() {
            @Override
            public Predicate toPredicate(Root<VendorEmail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(!StringUtils.isEmpty(filterText)) {

                    predicate = criteriaBuilder.or(
                            criteriaBuilder.or(predicate, criteriaBuilder.like(root.get("to"),"%"+ filterText +"%")),
                            criteriaBuilder.or(predicate, criteriaBuilder.like(root.get("from"),"%"+ filterText +"%")),
                            criteriaBuilder.or(predicate, criteriaBuilder.like(root.get("subject"),"%"+ filterText +"%")),
                            criteriaBuilder.or(predicate, criteriaBuilder.like(root.get("message"),"%"+ filterText +"%"))
                    );
                }


                return predicate;
            }
        };
    }
}
