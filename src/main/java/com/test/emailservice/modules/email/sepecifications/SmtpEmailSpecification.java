package com.test.emailservice.modules.email.sepecifications;

import com.test.emailservice.modules.auth.threads.AuthUserThread;
import com.test.emailservice.modules.email.entities.SmtpEmail;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SmtpEmailSpecification {
    public static Specification<SmtpEmail> filter(String filterText) {
        return new Specification<SmtpEmail>() {
            @Override
            public Predicate toPredicate(Root<SmtpEmail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate = criteriaBuilder.equal(root.get("user"), AuthUserThread.getContext());
                if(!StringUtils.isEmpty(filterText)) {
                    Predicate toPredicate = criteriaBuilder.like(root.get("to"), "%"+filterText+"%");
                    Predicate fromPredicate = criteriaBuilder.like(root.get("from"), "%"+filterText+"%");
                    Predicate subjectPredicate = criteriaBuilder.like(root.get("subject"), "%"+filterText+"%");
                    Predicate messagePredicate = criteriaBuilder.like(root.get("message"), "%"+filterText+"%");
                    Predicate orPredicate = criteriaBuilder.or(toPredicate, fromPredicate, subjectPredicate, messagePredicate);
                    predicate = criteriaBuilder.and(predicate, orPredicate);
                    return predicate;
                }
                predicate = criteriaBuilder.and(predicate);
                return predicate;
            }
        };
    }
}
