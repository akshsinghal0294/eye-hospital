package com.eyehospital.specification;

import com.eyehospital.entity.Patient;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PatientSpecification {

    private PatientSpecification() {
    }

    public static Specification<Patient> searchPatients(
            String mobile,
            LocalDateTime fromDate,
            LocalDateTime toDate) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Mobile Filter
            if (mobile != null && !mobile.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(root.get("mobile"), mobile)
                );
            }

            // Date Filter
            if (fromDate != null && toDate != null) {

                predicates.add(
                    criteriaBuilder.between(
                        root.get("createdAt"),
                        fromDate,
                        toDate
                    )
                );
            
            } else if (fromDate != null) {
            
                predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(
                        root.get("createdAt"),
                        fromDate
                    )
                );
            
            } else if (toDate != null) {
            
                predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(
                        root.get("createdAt"),
                        toDate
                    )
                );
            
            }
            // // Latest records first
            // query.orderBy(criteriaBuilder.desc(root.get("createdAt")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}