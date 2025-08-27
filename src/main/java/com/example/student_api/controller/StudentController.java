package com.example.student_api.controller;

import com.example.student_api.entity.Group;
import com.example.student_api.entity.Student;
import com.example.student_api.repository.specification.StudentSearchCriteria;
import com.example.student_api.service.GroupService;
import com.example.student_api.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final GroupService groupService;

    public StudentController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    // Все студенты
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Студент по id
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // Создать нового студента
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    // Обновить студента
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Student student = studentService.getStudentById(id);
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setMiddleName(studentDetails.getMiddleName());
        student.setGender(studentDetails.getGender());
        student.setBirthDate(studentDetails.getBirthDate());
        student.setStatus(studentDetails.getStatus());
        student.setGroup(studentDetails.getGroup());
        return studentService.saveStudent(student);
    }

    // Удалить студента
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // Найти студентов по группе
    @GetMapping("/group/{groupId}")
    public List<Student> getStudentsByGroup(@PathVariable Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return studentService.getStudentsByGroup(group);
    }

    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) String groupName,
            @RequestParam(required = false) String birthDateFrom,
            @RequestParam(required = false) String birthDateTo,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer course
    ) {
        StudentSearchCriteria criteria = new StudentSearchCriteria();
        criteria.setId(id);
        criteria.setFirstName(firstName);
        criteria.setLastName(lastName);
        criteria.setMiddleName(middleName);
        criteria.setGroupName(groupName);
        criteria.setGender(gender);
        criteria.setStatus(status);
        criteria.setCourse(course);
        if (birthDateFrom != null) criteria.setBirthDateFrom(java.time.LocalDate.parse(birthDateFrom));
        if (birthDateTo != null) criteria.setBirthDateTo(java.time.LocalDate.parse(birthDateTo));
        return studentService.searchStudents(criteria);
    }

    // Поиск по фамилии
    @GetMapping("/search/lastname")
    public List<Student> searchByLastName(@RequestParam String lastName) {
        return studentService.findByLastName(lastName);
    }

    // Поиск по имени
    @GetMapping("/search/firstname")
    public List<Student> searchByFirstName(@RequestParam String firstName) {
        return studentService.findByFirstName(firstName);
    }

    // Поиск по полу
    @GetMapping("/search/gender")
    public List<Student> searchByGender(@RequestParam String gender) {
        return studentService.findByGender(gender);
    }

    // Поиск по статусу
    @GetMapping("/search/status")
    public List<Student> searchByStatus(@RequestParam String status) {
        return studentService.findByStatus(status);
    }
}
