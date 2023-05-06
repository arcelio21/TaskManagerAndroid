package com.dev6am.todo.util;

import java.util.concurrent.atomic.AtomicLong;

public class GeneratorIDSubTask {

    private static AtomicLong idSubTaskIncrement;

    private GeneratorIDSubTask(){
    }

    public static synchronized AtomicLong  getGeneratorIdSubTask(){

        if (idSubTaskIncrement==null){
            idSubTaskIncrement = new AtomicLong();
        }

        return idSubTaskIncrement;
    }
}
