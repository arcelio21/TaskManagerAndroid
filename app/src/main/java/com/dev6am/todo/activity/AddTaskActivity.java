package com.dev6am.todo.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.dev6am.todo.util.DialogListener;
import com.dev6am.todo.util.GeneratorIdCategory;
import com.dev6am.todo.viewmodel.TaskViewModel;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity implements DialogListener {

    private TaskViewModel taskViewModel;

    private List<SubTask> subTaskList;
    private Button btnSubTask;
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




        if(!this.fileCategoryExist(this)){
            this.createDefaultCategory(this);
        }

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
    }

    private void showDialogAddSubTask(){
        AddSubTaskDialog dialogFragment = new AddSubTaskDialog();
        dialogFragment.show(getSupportFragmentManager(),"Add SubTask");

    }

    public void showSave(View view){

        this.getDataLayoutTask();
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

        this.subTaskAdapter = new SubTaskAdapter(subTaskList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.subTaskAdapter);
        recyclerView.smoothScrollToPosition(3);

        return  recyclerView;
    }

    private boolean fileCategoryExist(Context context){

        File file = new File(context.getFilesDir().getAbsolutePath(),"Categories.json");

        return file.exists() && file.length()>0;
    }


    private void createDefaultCategory(Context context) {

        Category category = Category.builder()
                .id(GeneratorIdCategory.getGeneratorIdCategory().incrementAndGet())
                .color(getColor(R.color.categoryDefault))
                .name("Default")
                .build();

        this.taskViewModel.addCategory(category,context);

    }

    private List<Category> getCategories(Context context){
        return this.taskViewModel.getCategories(context);
    }

    /**
     * FORMATO DE FECHA: yyyy-MM-dd
     */
    private void getDataLayoutTask(){

        TextView txtNameTask = findViewById(R.id.txtNameTask);
        TextView txtDateTask = findViewById(R.id.txtDateTask);
        Object category = this.spinnerCategory.getSelectedItem();
        Object priority = this.spinnerPriority.getSelectedItem();
        TextView txtMoreInformation = findViewById(R.id.txtMoreInformation);

        Log.println(Log.INFO,"TEST","FUNCIONO");
    }
}