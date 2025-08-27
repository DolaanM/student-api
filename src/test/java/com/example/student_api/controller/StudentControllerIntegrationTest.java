package com.example.student_api.controller;

import com.example.student_api.entity.Group;
import com.example.student_api.entity.Student;
import com.example.student_api.repository.GroupRepository;
import com.example.student_api.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Group group;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        groupRepository.deleteAll();

        group = new Group(null, "CS-101", "CS Department", 2023, null);
        group = groupRepository.save(group);
    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student(null, "John", "Doe", "M", group,
                LocalDate.of(2003, 5, 12), "M", "active");

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    void testGetAllStudents() throws Exception {
        Student student = new Student(null, "Alice", "Smith", "F", group,
                LocalDate.of(2004, 3, 20), "F", "active");
        studentRepository.save(student);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("Alice")));
    }

    @Test
    void testGetStudentById() throws Exception {
        Student student = new Student(null, "Bob", "Brown", "M", group,
                LocalDate.of(2002, 8, 15), "M", "active");
        student = studentRepository.save(student);

        mockMvc.perform(get("/api/students/{id}", student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Bob")));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student(null, "Charlie", "White", "M", group,
                LocalDate.of(2001, 1, 5), "M", "active");
        student = studentRepository.save(student);

        student.setFirstName("Charles");

        mockMvc.perform(put("/api/students/{id}", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Charles")));
    }

    @Test
    void testDeleteStudent() throws Exception {
        Student student = new Student(null, "Diana", "Green", "F", group,
                LocalDate.of(2000, 12, 30), "F", "active");
        student = studentRepository.save(student);

        mockMvc.perform(delete("/api/students/{id}", student.getId()))
                .andExpect(status().isNoContent());

        assertFalse(studentRepository.findById(student.getId()).isPresent());
    }
}
