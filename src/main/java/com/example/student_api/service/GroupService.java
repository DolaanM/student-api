package com.example.student_api.service;

import com.example.student_api.entity.Group;
import com.example.student_api.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    // Получить все группы
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // Получить группу по id
    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Группа с id " + id + " не найдена"));
    }

    // Создать/обновить группу
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    // Удалить группу
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Группа с id " + id + " не найдена");
        }
        groupRepository.deleteById(id);
    }

    // Поиск группы по имени
    public Group getGroupByName(String name) {
        return groupRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Группа с названием " + name + " не найдена"));
    }

    // Поиск по части имени
    public List<Group> searchGroupsByName(String namePart) {
        return groupRepository.findByNameContainingIgnoreCase(namePart);
    }

    // Поиск по кафедре
    public List<Group> searchGroupsByDepartment(String department) {
        return groupRepository.findByDepartmentContainingIgnoreCase(department);
    }

    // Поиск по году
    public List<Group> searchGroupsByYear(Integer year) {
        return groupRepository.findByYear(year);
    }
}
