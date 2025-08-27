package com.example.student_api.controller;

import com.example.student_api.entity.Group;
import com.example.student_api.entity.Student;
import com.example.student_api.service.GroupService;
import com.example.student_api.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GroupControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private GroupController groupController;

    private Group group;
    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).build();

        group = new Group(1L, "CS-101", "CS Dept", 2023, null);
        student = new Student(1L, "John", "Doe", "M", group, null, "M", "active");
    }

    @Test
    void testGetAllGroups() throws Exception {
        when(groupService.getAllGroups()).thenReturn(List.of(group));

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("CS-101"));
    }

    @Test
    void testGetGroupById() throws Exception {
        when(groupService.getGroupById(1L)).thenReturn(group);

        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("CS-101"));
    }

    @Test
    void testGetStudentsByGroup() throws Exception {
        when(groupService.getGroupById(1L)).thenReturn(group);
        when(studentService.getStudentsByGroup(group)).thenReturn(List.of(student));

        mockMvc.perform(get("/api/groups/1/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }
}
