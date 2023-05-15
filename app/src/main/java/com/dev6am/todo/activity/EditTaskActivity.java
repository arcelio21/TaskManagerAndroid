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
import android.widget.Toast;

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
import com.dev6am.todo.util.DialogListener;
import com.dev6am.todo.util.SubTaskListener;
import com.dev6am.todo.util.UpdateListenerDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EditTaskActivity extends AppCompatActivity implements DialogListener, SubTaskListener, UpdateListenerDate {

    private Task taskUpdate;
    private List<SubTask> subTaskList;
    private Button btnSubTask;
    private Button btnBack;
    private Button btnUpdate;
    private Button btnSelectDate;
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


        Intent intent = getIntent();
        Long idTask = intent.getLongExtra("ID_TASK",0);

        this.taskUpdate = Task.builder().build();

        this.taskUpdate.setId(idTask);

        this.loadComponentLayout();
        this.initData();
    }


    private void loadComponentLayout(){
        this.btnSubTask = findViewById(R.id.btnAddSubTaskUpdate);

        this.btnSubTask.setOnClickListener(view -> {
            AddSubTaskDialog addSubTaskDialog = new AddSubTaskDialog();
            addSubTaskDialog.show(getSupportFragmentManager(),"SUb task update");
        });

        this.btnBack = findViewById(R.id.btnBackTask);
        this.btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });

        this.btnSelectDate = findViewById(R.id.btnAddDateTaskUpdate);
        this.btnSelectDate.setOnClickListener(view -> {
            DialogDatePicker datePicker = new DialogDatePicker();
            datePicker.setUpdateListenerDate(this);
            datePicker.show(getSupportFragmentManager(),"DatePicker");
        });

        this.btnUpdate = findViewById(R.id.btnUpdateTask);
        this.btnUpdate.setOnClickListener(view -> {
            this.loadDataFromLayoutUpdate();
            TaskRepository taskRepository = new TaskRepository();

            boolean update = taskRepository.update(this.taskUpdate,this);

            if(update){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this,"can´t update task",Toast.LENGTH_SHORT).show();
            }

        });

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

            List<PriorityLevel> priorityLevels = new ArrayList<>();
            priorityLevels.add(this.taskUpdate.getPriorityLevel());

            priorityLevels.addAll((priorityLevels.size()),
                    Arrays.stream(PriorityLevel.values()).
                            filter((priorityLevel -> priorityLevel.compareTo(this.taskUpdate.getPriorityLevel())!=0))
                            .collect(Collectors.toList())
                    );

            SpinnerPriorityAdapter spinnerPriorityAdapter = new SpinnerPriorityAdapter(this,R.layout.layout_priority_level,R.id.txtNamePriority,priorityLevels);

            this.spinnerPriority.setAdapter(spinnerPriorityAdapter);


            this.subTaskList = (this.taskUpdate.getSubTasks().isEmpty())? new ArrayList<>():this.taskUpdate.getSubTasks();

            this.subTaskAdapter = new SubTaskAdapter(this.subTaskList,this);
            this.rvSubTaks.setLayoutManager(new LinearLayoutManager(this));
            this.rvSubTaks.setAdapter(this.subTaskAdapter);

        }
    }


    @Override
    public void setDataSubTask(SubTask subTask) {

        Log.d("EDIT TAKS", "SE VA A MOSTRAR EN PANTALLA");
        this.subTaskList.add(subTask);
        this.notifyChangeDataSubtaks();
    }

    private void notifyChangeDataSubtaks(){
        this.subTaskAdapter.setSubTaskList(this.subTaskList);
        this.subTaskAdapter.notifyItemInserted(this.subTaskList.size()-1);
    }


    private void loadDataFromLayoutUpdate(){

        this.taskUpdate.setTitle(this.etName.getText().toString());
        this.taskUpdate.setDate(this.etDate.getText().toString());
        this.taskUpdate.setTags((Category) this.spinnerCategory.getSelectedItem());
        this.taskUpdate.setPriorityLevel((PriorityLevel) this.spinnerPriority.getSelectedItem());
        this.taskUpdate.setBody(this.etMoreInformacion.getText().toString());
        this.taskUpdate.setSubTasks(this.subTaskList);
    }

    @Override
    public void remove(SubTask subTask) {

        int position = this.subTaskList.indexOf(subTask);
        this.subTaskList.remove(subTask);
        this.subTaskAdapter.notifyItemRemoved(position);
    }

    public String getDateTask(){
        return this.taskUpdate.getDate();
    }

    public void updateDate(String date){
        this.etDate.setText(date);
    }
}