package com.example.student_api.service;

import com.example.student_api.entity.Group;
import com.example.student_api.entity.Student;
import com.example.student_api.repository.StudentRepository;
import com.example.student_api.repository.specification.StudentSearchCriteria;
import com.example.student_api.repository.specification.StudentSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private Group group;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        group = new Group(1L, "CS-101", "CS Dept", 2023, null);
        student = new Student(1L, "John", "Doe", "M", group, LocalDate.of(2003, 5, 12), "M", "active");
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAllByOrderByLastNameAscFirstNameAscMiddleNameAsc())
                .thenReturn(List.of(student));

        List<Student> students = studentService.getAllStudents();
        assertThat(students).hasSize(1);
        assertThat(students.get(0).getFirstName()).isEqualTo("John");

        verify(studentRepository, times(1))
                .findAllByOrderByLastNameAscFirstNameAscMiddleNameAsc();
    }

    @Test
    void testGetStudentByIdExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1L);
        assertThat(result.getFirstName()).isEqualTo("John");
    }

    @Test
    void testGetStudentByIdNotFound() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> studentService.getStudentById(2L));
    }

    @Test
    void testSaveStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.saveStudent(student);
        assertThat(result.getLastName()).isEqualTo("Doe");
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testDeleteStudentExists() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteStudentNotFound() {
        when(studentRepository.existsById(2L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> studentService.deleteStudent(2L));
    }

    @Test
    void testGetStudentsByGroup() {
        when(studentRepository.findByGroupOrderByLastNameAscFirstNameAscMiddleNameAsc(group))
                .thenReturn(List.of(student));

        List<Student> students = studentService.getStudentsByGroup(group);
        assertThat(students).hasSize(1);
        assertThat(students.get(0).getGroup().getName()).isEqualTo("CS-101");
    }

    @Test
    void testFindByLastName() {
        when(studentRepository.findByLastNameIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc("Doe"))
                .thenReturn(List.of(student));

        List<Student> students = studentService.findByLastName("Doe");
        assertThat(students).hasSize(1);
        assertThat(students.get(0).getLastName()).isEqualTo("Doe");
    }

    @Test
    void testFindByFirstName() {
        when(studentRepository.findByFirstNameIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc("John"))
                .thenReturn(List.of(student));

        List<Student> students = studentService.findByFirstName("John");
        assertThat(students).hasSize(1);
        assertThat(students.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    void testFindByGender() {
        when(studentRepository.findByGenderIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc("M"))
                .thenReturn(List.of(student));

        List<Student> students = studentService.findByGender("M");
        assertThat(students).hasSize(1);
        assertThat(students.get(0).getGender()).isEqualTo("M");
    }

    @Test
    void testFindByStatus() {
        when(studentRepository.findByStatusIgnoreCaseOrderByLastNameAscFirstNameAscMiddleNameAsc("active"))
                .thenReturn(List.of(student));

        List<Student> students = studentService.findByStatus("active");
        assertThat(students).hasSize(1);
        assertThat(students.get(0).getStatus()).isEqualTo("active");
    }

    @Test
    void testSearchStudents() {
        StudentSearchCriteria criteria = new StudentSearchCriteria();
        when(studentRepository.findAll(any(org.springframework.data.jpa.domain.Specification.class)))
                .thenReturn(List.of(student));

        List<Student> students = studentService.searchStudents(criteria);
        assertThat(students).hasSize(1);
        verify(studentRepository, times(1)).findAll(any(org.springframework.data.jpa.domain.Specification.class));
    }
}
