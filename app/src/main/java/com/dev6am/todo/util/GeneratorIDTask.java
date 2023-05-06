package com.dev6am.todo.util;

import java.util.concurrent.atomic.AtomicLong;

public class GeneratorIDTask {

    private static AtomicLong idTaskIncrement;

    private GeneratorIDTask(){
    }

    public static synchronized AtomicLong  getGeneratorIdTask(){

        if (idTaskIncrement==null){
            idTaskIncrement = new AtomicLong();
        }

        return idTaskIncrement;
    }
}
