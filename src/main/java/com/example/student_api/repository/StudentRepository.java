package com.example.student_api.repository;

import com.example.student_api.entity.Student;
import com.example.student_api.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    List<Student> findAllByOrderByLastNameAscFirstNameAscMiddleNameAsc();

    List<Student> findByGroupOrderByLastNameAscFirstNameAscMiddleNameAsc(Group group);

    List<Student> findByLastNameIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc(String lastName);
    List<Student> findByFirstNameIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc(String firstName);
    List<Student> findByGenderIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc(String gender);
    List<Student> findByStatusIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc(String status);
}
