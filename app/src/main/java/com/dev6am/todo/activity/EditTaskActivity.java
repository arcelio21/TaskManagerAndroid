package com.dev6am.todo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.dev6am.todo.R;
import com.dev6am.todo.adapter.SpinnerCategoryAdapter;
import com.dev6am.todo.adapter.SpinnerPriorityAdapter;
import com.dev6am.todo.adapter.SubTaskAdapter;
import com.dev6am.todo.model.Category;
import com.dev6am.todo.model.PriorityLevel;
import com.dev6am.todo.model.SubTask;
import com.dev6am.todo.model.Task;
import com.dev6am.todo.repository.CategoryRepository;
import com.dev6am.todo.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EditTaskActivity extends AppCompatActivity {

    private Task taskUpdate;
    private List<SubTask> subTaskList;
    private Button btnSubTask;
    private Button btnBack;
    private Button btnUpdate;
    private RecyclerView rvSubTaks;
    private SubTaskAdapter subTaskAdapter;
    private Spinner spinnerCategory;
    private Spinner spinnerPriority;

    private EditText etName;
    private EditText etDate;
    private EditText etMoreInformacion;

    private List<Category> categories;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Log.d("CREATE", "CREADO EDITTASK");

        Intent intent = getIntent();
        Long idTask = intent.getLongExtra("ID_TASK",0);

        this.taskUpdate = Task.builder().build();

        this.taskUpdate.setId(idTask);

        this.loadComponentLayout();
        this.initData();
    }

    @Override
    protected void onStart() {
        Log.d("METODO START", "ESTA EN METODO START");
        super.onStart();
    }

    @Override
    protected void onResume() {

        Log.d("METODO ON RESUME", "ESTOY EN RESUME");
        super.onResume();
    }

    private void loadComponentLayout(){
        this.btnSubTask = findViewById(R.id.btnAddSubTaskUpdate);
        this.btnBack = findViewById(R.id.btnBackTask);
        this.btnUpdate = findViewById(R.id.btnUpdateTask);

        this.rvSubTaks = findViewById(R.id.RVSubTaskUPdate);
        this.spinnerCategory = findViewById(R.id.spinCategoryUpdate);
        this.spinnerPriority = findViewById(R.id.spinPriorityUpdate);
        this.etDate = findViewById(R.id.etDateTaskUpdate);
        this.etMoreInformacion = findViewById(R.id.etMoreInformationUpdate);
        this.etName = findViewById(R.id.etNameTaskUpdate);
    }

    private void initData(){
        TaskRepository taskRepository = new TaskRepository();

        this.taskUpdate = taskRepository.getById(taskUpdate.getId(),this);

        if(taskUpdate!=null){

            CategoryRepository categoryRepository = new CategoryRepository();

            this.etName.setText(this.taskUpdate.getTitle());
            this.etDate.setText(this.taskUpdate.getDate());
            this.etMoreInformacion.setText(this.taskUpdate.getBody());

            this.categories = new ArrayList<>();
            this.categories.add(taskUpdate.getTags());

            this.categories.addAll(this.categories.size(),

                    categoryRepository.getCategories(this)
                            .stream()
                            .filter((categoryF-> !categoryF.getId().equals(this.taskUpdate.getTags().getId())))
                            .collect(Collectors.toList()));

            SpinnerCategoryAdapter categoryAdapter = new SpinnerCategoryAdapter(this,R.layout.layout_categories,R.id.txtNameCategory,this.categories);
            this.spinnerCategory.setAdapter(categoryAdapter);
            this.spinnerCategory.setSelection(0);

            List<PriorityLevel> priorityLevels = new ArrayList<>();
            priorityLevels.add(this.taskUpdate.getPriorityLevel());

            priorityLevels.addAll((priorityLevels.size()),
                    Arrays.stream(PriorityLevel.values()).
                            filter((priorityLevel -> priorityLevel.compareTo(this.taskUpdate.getPriorityLevel())!=0))
                            .collect(Collectors.toList())
                    );

            SpinnerPriorityAdapter spinnerPriorityAdapter = new SpinnerPriorityAdapter(this,R.layout.layout_priority_level,R.id.txtNamePriority,priorityLevels);

            this.spinnerPriority.setAdapter(spinnerPriorityAdapter);
            this.spinnerPriority.setSelection(0);


            this.subTaskList = (this.taskUpdate.getSubTasks().isEmpty())? Collections.emptyList():this.taskUpdate.getSubTasks();

            this.subTaskAdapter = new SubTaskAdapter(this.subTaskList);
            this.rvSubTaks.setLayoutManager(new LinearLayoutManager(this));
            this.rvSubTaks.setAdapter(this.subTaskAdapter);

        }
    }


}