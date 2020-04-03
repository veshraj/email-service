package com.test.emailservice.modules.users.specifications;

import com.test.emailservice.modules.users.entities.User;
import com.test.emailservice.modules.users.resources.UserListRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class UserSpecification {
    public static Specification<User> filter(UserListRequest request) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (Objects.nonNull(request)) {
                    System.out.println("we are upto here right inside the predicate, now request body is also not null");
                    if (!StringUtils.isEmpty(request.getName())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
                    }
                    if (!StringUtils.isEmpty(request.getEmail())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
                    }
                    if (!StringUtils.isEmpty(request.getMobileNumber())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("mobileNumber"), "%" + request.getMobileNumber() + "%"));
                    }
                    if (!StringUtils.isEmpty(request.getWebsite())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("website"), "%" + request.getWebsite() + "%"));
                    }
                }
                return predicate;
            }
        };
    }

}