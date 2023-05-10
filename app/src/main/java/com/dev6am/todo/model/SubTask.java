package com.dev6am.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class SubTask {

    private Long id;
    private String name;
    private boolean checked=false;
}
