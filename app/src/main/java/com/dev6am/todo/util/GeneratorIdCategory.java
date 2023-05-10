package com.dev6am.todo.util;

import java.util.concurrent.atomic.AtomicLong;


public class GeneratorIdCategory {

    private static AtomicLong idCategoryIncrement;

    private GeneratorIdCategory() {
    }

    public static synchronized AtomicLong  getGeneratorIdCategory(){

        if (idCategoryIncrement==null){
            idCategoryIncrement = new AtomicLong();
        }

        return idCategoryIncrement;
    }
}
