package com.dev6am.todo.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SubTask {

    private Long id;
    private String name;
    private boolean checked=false;
}
