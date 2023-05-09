package com.dev6am.todo.viewmodel;

import androidx.lifecycle.ViewModel;

import com.dev6am.todo.model.Task;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskViewModel extends ViewModel {


    private Task task;
    private AtomicInteger generatorID;

    public TaskViewModel() {
    }


}
