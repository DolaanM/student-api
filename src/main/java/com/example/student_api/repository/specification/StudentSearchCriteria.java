package com.example.student_api.repository.specification;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentSearchCriteria {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String groupName;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private String gender;
    private String status;
    private Integer course;
}
