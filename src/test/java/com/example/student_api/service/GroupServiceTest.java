package com.example.student_api.service;

import com.example.student_api.entity.Group;
import com.example.student_api.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupServiceTest {

    private GroupRepository groupRepository;
    private GroupService groupService;

    private Group group;

    @BeforeEach
    void setUp() {
        groupRepository = mock(GroupRepository.class);
        groupService = new GroupService(groupRepository);

        group = new Group(1L, "CS-101", "CS Dept", 2023, null);
    }

    @Test
    void testGetAllGroups() {
        when(groupRepository.findAll()).thenReturn(List.of(group));
        List<Group> result = groupService.getAllGroups();
        assertEquals(1, result.size());
        verify(groupRepository, times(1)).findAll();
    }

    @Test
    void testGetGroupById() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        Optional<Group> result = Optional.ofNullable(groupService.getGroupById(1L));
        assertTrue(result.isPresent());
        assertEquals("CS-101", result.get().getName());
    }
}
