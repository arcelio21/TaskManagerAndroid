package com.dev6am.todo.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.dev6am.todo.model.Category;
import com.dev6am.todo.model.Data;
import com.dev6am.todo.model.Task;
import com.dev6am.todo.repository.CategoryRepository;
import com.dev6am.todo.repository.TaskRepository;
import com.dev6am.todo.util.GeneratorIdCategory;
import com.dev6am.todo.util.ReaderWriterFIle;

import java.util.List;

public class TaskViewModel extends ViewModel {


    private static final CategoryRepository categoryRepository= new CategoryRepository();
    private static final TaskRepository taskRepository = new TaskRepository();


    public TaskViewModel() {
    }


    public boolean addCategory(Category category, Context context){

        return TaskViewModel.categoryRepository.addCategory(category,context);

    }

    public List<Category> getCategories(Context context){

        return TaskViewModel.categoryRepository.getCategories(context);
    }

    public boolean addTaks(Task task, Context context){

        return TaskViewModel.taskRepository.saveTask(task,context);

    }
}
