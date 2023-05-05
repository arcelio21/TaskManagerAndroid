package com.dev6am.todo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.dev6am.todo.R;

public class MainActivity extends AppCompatActivity {

    private  String NAME_FILE_SP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NAME_FILE_SP =  getString(R.string.nameSharePreferencesFIle);

        if(validateUserNotExist()){
            showDialogUser();
        }


    }


    public void showDialogTask(View view){
        //showDialogTask();
        showAddTaskActivity();
    }

    private void showAddTaskActivity(){
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);

    }

    private void showDialogTask(){
        DialogFragment dialogFragment = new AddTaskDialog();
        dialogFragment.show(getSupportFragmentManager(),"Dialog add Task");
    }
    /**
     * Muestra la ventana de dialogo la primera vez que el usuario ingrese su usuario
     * o lo quiera cambiar
     */
    private void showDialogUser(){

        DialogFragment dialogFragment = new AddUserDialog();
        dialogFragment.show(getSupportFragmentManager(),"Dialog Add User");
    }

    /**
     * Valida si no se ha creado un usuario
     * @return true : en caso de que no exista el usuario
     */
    private boolean validateUserNotExist(){
        SharedPreferences sharedPreferences = getSharedPreferences(this.NAME_FILE_SP, Context.MODE_PRIVATE);
        String userCreate = sharedPreferences.getString("user",null);

        return userCreate==null || userCreate.trim().equals("");
    }




}