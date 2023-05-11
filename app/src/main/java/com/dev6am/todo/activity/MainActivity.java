package com.dev6am.todo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev6am.todo.R;
import com.dev6am.todo.adapter.TaskAdapter;
import com.dev6am.todo.model.Task;
import com.dev6am.todo.model.User;
import com.dev6am.todo.util.SelectListener;
import com.dev6am.todo.viewmodel.MainViewModel;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener {

    private MainViewModel mainViewModel;
    private RecyclerView rvTaks;
    private List<Task> taskList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel= new MainViewModel();
        this.rvTaks = findViewById(R.id.rvShowTaks);


        if(validateUserNotExist(this)){
            this.showDialogUser(); //ESTO ES UN PROCESO ASINCRONO
        }else {
            this.setInfoUser(this);
        }

        if (this.validateFileTaskExits(this)){
            this.taskList = this.mainViewModel.getTasks(this);
            this.showInfoRecycleView(this.rvTaks);
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

    private boolean validateFileTaskExits(Context context){

        File file = new File(context.getFilesDir().getAbsolutePath(), "Tasks.json");
        return file.exists() && file.length()>0;
    }


    private void setInfoUser(Context context){

        User user = this.mainViewModel.getUser(context);

        Button button = findViewById(R.id.btnAdd);

        button.setText(user.getUserName());
    }


    private void showInfoRecycleView(RecyclerView rvTaks){

        TaskAdapter taskAdapter = new TaskAdapter(this.taskList,this);

        rvTaks.setLayoutManager(new LinearLayoutManager(this));
        rvTaks.setAdapter(taskAdapter);
    }

    /**
     * SE UTILIZA PARA ASIGNAR LA POSICION DE LA TAREA QUE SE QUIERE VER TODA LA INFORMACION
     * @param position
     */
    @Override
    public void setTaskSelectMoreInfoListener(int position) {

        Toast.makeText(this,"Title: "+this.taskList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
    }
}