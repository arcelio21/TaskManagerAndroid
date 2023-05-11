package com.dev6am.todo.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Task {

    private Long id;
    private String title;
    private String body;
    private String date;

    private PriorityLevel priorityLevel;

    private Category tags;

    private List<SubTask> subTasks;

    private  boolean checked=false;

}
