package com.dev6am.todo.util;

import android.util.Log;

import java.util.concurrent.atomic.AtomicLong;


public class GeneratorIdCategory {

    private static AtomicLong idCategoryIncrement;

    private GeneratorIdCategory() {
        Log.d("GENERATOR CATEGORY", "SE CREO NUEVA INSTANCIA");
    }

    public static synchronized AtomicLong  getGeneratorIdCategory(){

        if (idCategoryIncrement==null){
            idCategoryIncrement = new AtomicLong();
        }

        Log.d("GENERATOR CATEGORY", "NO SE CREO NUEVA INSTANCIA");

        return idCategoryIncrement;
    }
}
