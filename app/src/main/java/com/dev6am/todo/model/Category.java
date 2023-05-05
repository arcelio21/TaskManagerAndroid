package com.dev6am.todo.model;

import lombok.Builder;

@Builder
public class Category {

    private Long id;
    private String name;
    private String color;
}
