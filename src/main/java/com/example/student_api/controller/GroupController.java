package com.example.student_api.controller;

import com.example.student_api.entity.Group;
import com.example.student_api.entity.Student;
import com.example.student_api.service.GroupService;
import com.example.student_api.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    private final StudentService studentService;

    public GroupController(GroupService groupService, StudentService studentService) {
        this.groupService = groupService;
        this.studentService = studentService;
    }

    // Все группы
    @GetMapping
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    // Группа по id
    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    // Создать новую группу
    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.saveGroup(group);
    }

    // Обновить группу
    @PutMapping("/{id}")
    public Group updateGroup(@PathVariable Long id, @RequestBody Group groupDetails) {
        Group group = groupService.getGroupById(id);
        group.setName(groupDetails.getName());
        group.setDepartment(groupDetails.getDepartment());
        group.setYear(groupDetails.getYear());
        return groupService.saveGroup(group);
    }

    // Удалить группу
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsByGroup(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        return studentService.getStudentsByGroup(group);
    }

    // Поиск по имени группы
    @GetMapping("/search/name")
    public Group getGroupByName(@RequestParam String name) {
        return groupService.getGroupByName(name);
    }

    // Поиск по части имени
    @GetMapping("/search/name/like")
    public List<Group> searchGroupsByName(@RequestParam String name) {
        return groupService.searchGroupsByName(name);
    }

    // Поиск по кафедре
    @GetMapping("/search/department")
    public List<Group> searchGroupsByDepartment(@RequestParam String department) {
        return groupService.searchGroupsByDepartment(department);
    }

    // Поиск по году
    @GetMapping("/search/year")
    public List<Group> searchGroupsByYear(@RequestParam Integer year) {
        return groupService.searchGroupsByYear(year);
    }
}
