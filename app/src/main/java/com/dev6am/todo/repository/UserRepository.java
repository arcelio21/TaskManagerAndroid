package com.dev6am.todo.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import com.dev6am.todo.activity.AddUserDialog;
import com.dev6am.todo.model.Data;
import com.dev6am.todo.model.User;
import com.dev6am.todo.util.ConverterJson;
import com.dev6am.todo.util.ReaderWriterFIle;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class UserRepository {

    private final String NAME_FILE="UserData.json";

    public void addUser(String userAdd, Context context, String fileName){

        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", userAdd);
        editor.apply();
    }

    public boolean saveUserJson(User user, Context context){

        Data data = Data.builder()
                .data(user)
                .build();

        String dataJson = ConverterJson.DataToJson(data);

        return ReaderWriterFIle.writeFileJson(dataJson,context.getFilesDir().getAbsolutePath(),this.NAME_FILE);
    }


    public User getUser(Context context){

        String dataJson = ReaderWriterFIle.readFileJson(context.getFilesDir().getAbsolutePath(),this.NAME_FILE);
        User data= ConverterJson.jsonToData(dataJson, User.class);


        return (User) data;
    }

}
