package com.example.student_api.controller;

import com.example.student_api.entity.Group;
import com.example.student_api.entity.Student;
import com.example.student_api.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student student;
    private Group group;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        group = new Group(1L, "CS-101", "CS Dept", 2023, null);
        student = new Student(1L, "John", "Doe", "M", group, LocalDate.of(2003, 5, 12), "M", "active");
    }

    @Test
    void testGetAllStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(student));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void testGetStudentById() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }
}
