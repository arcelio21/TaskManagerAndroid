package com.dev6am.todo.repository;

import android.content.Context;
import android.content.SharedPreferences;
import com.dev6am.todo.activity.AddUserDialog;

public class UserRepository {


    public void addUser(String userAdd, Context context, String fileName){

        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", userAdd);
        editor.apply();
    }
}
