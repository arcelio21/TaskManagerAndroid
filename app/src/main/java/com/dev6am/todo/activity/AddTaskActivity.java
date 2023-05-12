package com.dev6am.todo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev6am.todo.R;
import com.dev6am.todo.adapter.SpinnerCategoryAdapter;
import com.dev6am.todo.adapter.SpinnerPriorityAdapter;
import com.dev6am.todo.adapter.SubTaskAdapter;
import com.dev6am.todo.model.Category;
import com.dev6am.todo.model.PriorityLevel;
import com.dev6am.todo.model.SubTask;
import com.dev6am.todo.model.Task;
import com.dev6am.todo.util.DialogListener;
import com.dev6am.todo.util.GeneratorIDTask;
import com.dev6am.todo.util.SubTaskListener;
import com.dev6am.todo.viewmodel.TaskViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity implements DialogListener, SubTaskListener {

    private TaskViewModel taskViewModel;

    private List<SubTask> subTaskList;
    private Button btnSubTask;
    private Button btnCancel;
    private RecyclerView rvSubTaks;
    private SubTaskAdapter subTaskAdapter;
    private Spinner spinnerCategory;
    private Spinner spinnerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        subTaskList=new ArrayList<>();
        taskViewModel= new ViewModelProvider(this).get(TaskViewModel.class);

        this.spinnerCategory=findViewById(R.id.spinCategory);




        this.loadSpinnerPriority();
        this.loadSpinnerCategory();

        this.rvSubTaks=this.implementRecycleView(findViewById(R.id.RVSubTask));

        /*
        * BOTON PARA MOSTRAR DIALOGO DE SUBTAREA
        * */
        btnSubTask= findViewById(R.id.btnAddSubTask);
        btnSubTask.setOnClickListener(view -> {
            this.showDialogAddSubTask();
        });

        btnCancel = findViewById(R.id.btnCancelTask);
        btnCancel.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });
    }

    private void showDialogAddSubTask(){
        AddSubTaskDialog dialogFragment = new AddSubTaskDialog();
        dialogFragment.show(getSupportFragmentManager(),"Add SubTask");

    }

    public void showSave(View view){
        Task task= this.getDataLayoutTask();
        this.taskViewModel.addTaks(task,this);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void loadSpinnerPriority(){

        this.spinnerPriority=findViewById(R.id.spinPriority);
        SpinnerPriorityAdapter spinnerPriorityAdapter = new SpinnerPriorityAdapter(this,R.layout.layout_priority_level,R.id.txtNamePriority ,Arrays.asList(PriorityLevel.values()));

        this.spinnerPriority.setAdapter(spinnerPriorityAdapter);

    }

    private void loadSpinnerCategory(){

        List<Category> categories = this.getCategories(this);

        SpinnerCategoryAdapter spinnerCategoryAdapter = new SpinnerCategoryAdapter(this,R.layout.layout_categories,R.id.txtNameCategory, categories);

        this.spinnerCategory.setAdapter(spinnerCategoryAdapter);
    }

    @Override
    public void setDataSubTask(SubTask subTask) {

        if(subTaskList!=null){
            subTaskList.add(subTask);
            this.addSubTaskRecycleView();
        }else {
            throw new RuntimeException("List SubTask is not Created");
        }
    }

    private void addSubTaskRecycleView(){
        this.subTaskAdapter.setSubTaskList(subTaskList);
        this.subTaskAdapter.notifyItemInserted(subTaskList.size()-1);
    }


    private RecyclerView implementRecycleView(RecyclerView recyclerView){

        this.subTaskAdapter = new SubTaskAdapter(subTaskList,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.subTaskAdapter);
        recyclerView.smoothScrollToPosition(3);

        return  recyclerView;
    }




    private List<Category> getCategories(Context context){
        return this.taskViewModel.getCategories(context);
    }

    /**
     * FORMATO DE FECHA: yyyy/MM/dd
     */
    private Task getDataLayoutTask(){

        TextView txtNameTask = findViewById(R.id.txtNameTask);
        TextView txtDateTask = findViewById(R.id.txtDateTask);
        Object category = this.spinnerCategory.getSelectedItem();
        Object priority = this.spinnerPriority.getSelectedItem();
        TextView txtMoreInformation = findViewById(R.id.txtMoreInformation);

        LocalDate date=null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            DateTimeFormatter  dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
           date = LocalDate.parse(txtDateTask.getText(),dtf);
        }


        return Task.builder()
                .id(GeneratorIDTask.getGeneratorIdTask().incrementAndGet())
                .title(txtNameTask.getText().toString())
                .body(txtMoreInformation.getText().toString())
                .date(date.toString())
                .checked(false)
                .priorityLevel((PriorityLevel) priority)
                .subTasks(this.subTaskList)
                .tags((Category) category)
                .build();
    }

    @Override
    public void remove(SubTask subTask) {

        int position = this.subTaskList.indexOf(subTask);
        this.subTaskList.remove(subTask);
        this.subTaskAdapter.notifyItemRemoved(position);
    }
}