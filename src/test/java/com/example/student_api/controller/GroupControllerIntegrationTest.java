package com.example.student_api.controller;

import com.example.student_api.entity.Group;
import com.example.student_api.entity.Student;
import com.example.student_api.repository.GroupRepository;
import com.example.student_api.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GroupControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    private Group group;
    private Student student;

    @BeforeEach
    void setup() {
        studentRepository.deleteAll();
        groupRepository.deleteAll();

        group = groupRepository.save(new Group(null, "CS-101", "CS Dept", 2023, null));
        student = studentRepository.save(new Student(null, "John", "Doe", "M", group, LocalDate.of(2003, 5, 12), "M", "active"));
    }

    @Test
    void testGetAllGroups() throws Exception {
        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("CS-101"));
    }

    @Test
    void testGetGroupById() throws Exception {
        mockMvc.perform(get("/api/groups/" + group.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CS-101"));
    }

    @Test
    void testGetStudentsByGroup() throws Exception {
        mockMvc.perform(get("/api/groups/" + group.getId() + "/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }
}
