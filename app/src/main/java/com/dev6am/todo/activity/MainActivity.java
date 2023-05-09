package com.dev6am.todo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.dev6am.todo.R;
import com.dev6am.todo.model.User;
import com.dev6am.todo.viewmodel.MainViewModel;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel= new MainViewModel();

        if(validateUserNotExist(this)){
            showDialogUser(); //ESTO ES UN PROCESO ASINCRONO
        }else {
            this.setInfoUser(this);
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
     * @param context
     */
    private boolean validateUserNotExist(Context context){

        File file = new File(context.getFilesDir().getAbsolutePath(), "UserData.json");
        return !file.exists()|| file.length()<=0;
    }


    private void setInfoUser(Context context){

        User user = this.mainViewModel.getUser(context);

        Button button = findViewById(R.id.btnAdd);

        button.setText(user.getUserName());
    }






}