package com.dev6am.todo.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Category {

    private Long id;
    private String name;
    private Integer color;

    @Override
    public String toString() {
        return name;
    }
}
