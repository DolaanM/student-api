package com.example.student_api.service;

import com.example.student_api.entity.Group;
import com.example.student_api.entity.Student;
import com.example.student_api.repository.StudentRepository;
import com.example.student_api.repository.specification.StudentSearchCriteria;
import com.example.student_api.repository.specification.StudentSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAllByOrderByLastNameAscFirstNameAscMiddleNameAsc();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Студент с id " + id + " не найден"));
    }

    // если тебе удобнее возвращать Optional, можно добавить:
    public Optional<Student> findStudentOptionalById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Студент с id " + id + " не найден");
        }
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByGroup(Group group) {
        return studentRepository.findByGroupOrderByLastNameAscFirstNameAscMiddleNameAsc(group);
    }

    // === НОВЫЙ метод гибкого поиска ===
    public List<Student> searchStudents(StudentSearchCriteria criteria) {
        return studentRepository.findAll(StudentSpecification.build(criteria));
    }


// Поиск студентов по фамилии
    public List<Student> findByLastName(String lastName) {
        return studentRepository.findByLastNameIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc(lastName);
    }

    // Поиск студентов по имени
    public List<Student> findByFirstName(String firstName) {
        return studentRepository.findByFirstNameIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc(firstName);
    }

    // Поиск студентов по полу
    public List<Student> findByGender(String gender) {
        return studentRepository.findByGenderIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc(gender);
    }

    // Поиск студентов по статусу
    public List<Student> findByStatus(String status) {
        return studentRepository.findByStatusIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc(status);
    }

}
