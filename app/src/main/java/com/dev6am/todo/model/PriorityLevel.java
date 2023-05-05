package com.dev6am.todo.model;

public enum PriorityLevel {

    HIGH,
    MEDIUM,
    LOW;

    public static PriorityLevel setValueInt(int num){

        PriorityLevel[] priority= PriorityLevel.values();

        for (PriorityLevel pl : priority ){

            if(num==pl.ordinal()){
                    return pl;
            }
        }

        throw new IllegalArgumentException("Valor no valido");

    }
}
