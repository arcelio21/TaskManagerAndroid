package com.dev6am.todo.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.dev6am.todo.model.Task;
import com.dev6am.todo.model.User;
import com.dev6am.todo.repository.TaskRepository;
import com.dev6am.todo.repository.UserRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final static UserRepository userRepository=new UserRepository();
    private final  static TaskRepository taskRepository = new TaskRepository();

    public User getUser(Context context){

        return MainViewModel.userRepository.getUser(context);
    }

    public List<Task> getTasks(Context context){

        return MainViewModel.taskRepository.getTasks(context);
    }
}
