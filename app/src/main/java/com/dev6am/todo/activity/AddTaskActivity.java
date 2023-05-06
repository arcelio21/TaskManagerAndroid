package com.dev6am.todo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dev6am.todo.R;
import com.dev6am.todo.model.SubTask;
import com.dev6am.todo.util.DialogListener;
import com.dev6am.todo.viewmodel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity implements DialogListener {

    private TaskViewModel taskViewModel;

    private List<SubTask> subTaskList;
    private Button btnSubTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        subTaskList=new ArrayList<>();
        taskViewModel= new ViewModelProvider(this).get(TaskViewModel.class);

        /*
        * BOTON PARA MOSTRAR DIALOGO DE SUBTAREA
        * */
        btnSubTask= findViewById(R.id.btnAddSubTask);
        btnSubTask.setOnClickListener(view -> {
            this.showDialogAddSubTask();
        });
    }

    private void showDialogAddSubTask(){
        AddSubTaskDialog dialogFragment = new AddSubTaskDialog();
        dialogFragment.show(getSupportFragmentManager(),"Add SubTask");

    }

    public void showSave(View view){
        Log.println(Log.INFO,"TEST SEND DATA", subTaskList.get(0).getName());
    }

    @Override
    public void setDataSubTask(SubTask subTask) {

        if(subTaskList!=null){
            subTaskList.add(subTask);
        }else {
            throw new RuntimeException("List SubTask is not Created");
        }
    }
}