package com.dev6am.todo.model;

import lombok.Builder;

@Builder
public class SubTask {

    private Long id;
    private String name;
    private boolean checked=false;
}
