package com.dev6am.todo.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity implements DialogListener {

    private TaskViewModel taskViewModel;

    private List<SubTask> subTaskList;
    private Button btnSubTask;
    private RecyclerView recyclerView;
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
        this.spinnerPriority=findViewById(R.id.spinPriority);

        if(!this.fileCategoryExist(this)){
            this.createDefaultCategory(this);
        }

        SpinnerPriorityAdapter spinnerPriorityAdapter = new SpinnerPriorityAdapter(this,R.layout.layout_priority_level,R.id.txtNamePriority ,Arrays.asList(PriorityLevel.values()));

        this.spinnerPriority.setAdapter(spinnerPriorityAdapter);

        List<Category> categories = this.getCategories(this);

        SpinnerCategoryAdapter spinnerCategoryAdapter = new SpinnerCategoryAdapter(this,R.layout.layout_categories,R.id.txtNameCategory, categories);

        this.spinnerCategory.setAdapter(spinnerCategoryAdapter);

        this.recyclerView=this.implementRecycleView(findViewById(R.id.RVSubTask));

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
            this.addSubTaskRecycleView(subTask);
        }else {
            throw new RuntimeException("List SubTask is not Created");
        }
    }

    private void addSubTaskRecycleView(SubTask subTask){
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
}