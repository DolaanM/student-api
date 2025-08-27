package com.example.student_api.repository;

import com.example.student_api.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByName(String name);

    List<Group> findByNameContainingIgnoreCase(String namePart);

    List<Group> findByDepartmentContainingIgnoreCase(String department);

    List<Group> findByYear(Integer year);
}
