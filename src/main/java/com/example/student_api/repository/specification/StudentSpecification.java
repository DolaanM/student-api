package com.example.student_api.repository.specification;

import com.example.student_api.entity.Group;
import com.example.student_api.entity.Student;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<Student> build(StudentSearchCriteria criteria) {
        return (Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria == null) {
                query.orderBy(cb.asc(root.get("lastName")), cb.asc(root.get("firstName")));
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            if (criteria.getId() != null) {
                predicates.add(cb.equal(root.get("id"), criteria.getId()));
            }
            if (criteria.getFirstName() != null && !criteria.getFirstName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + criteria.getFirstName().toLowerCase() + "%"));
            }
            if (criteria.getLastName() != null && !criteria.getLastName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + criteria.getLastName().toLowerCase() + "%"));
            }
            if (criteria.getMiddleName() != null && !criteria.getMiddleName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("middleName")), "%" + criteria.getMiddleName().toLowerCase() + "%"));
            }
            if (criteria.getGender() != null && !criteria.getGender().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("gender")), criteria.getGender().toLowerCase()));
            }
            if (criteria.getStatus() != null && !criteria.getStatus().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("status")), criteria.getStatus().toLowerCase()));
            }
            if (criteria.getBirthDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("birthDate"), criteria.getBirthDateFrom()));
            }
            if (criteria.getBirthDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("birthDate"), criteria.getBirthDateTo()));
            }
            if (criteria.getGroupName() != null && !criteria.getGroupName().isBlank()) {
                Join<Student, Group> groupJoin = root.join("group", JoinType.INNER);
                predicates.add(cb.like(cb.lower(groupJoin.get("name")), "%" + criteria.getGroupName().toLowerCase() + "%"));
            }
            if (criteria.getCourse() != null) {
                // Вычисляем ожидаемый year набора: year = currentYear - course + 1
                int currentYear = java.time.LocalDate.now().getYear();
                int expectedYear = currentYear - criteria.getCourse() + 1;
                Join<Student, Group> groupJoin = root.join("group", JoinType.INNER);
                predicates.add(cb.equal(groupJoin.get("year"), expectedYear));
            }

            // Сортировка по ФИО
            query.orderBy(cb.asc(root.get("lastName")), cb.asc(root.get("firstName")), cb.asc(root.get("middleName")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
