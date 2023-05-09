package com.dev6am.todo.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.dev6am.todo.model.User;
import com.dev6am.todo.repository.UserRepository;

public class MainViewModel extends ViewModel {

    private final static UserRepository userRepository=new UserRepository();

    public User getUser(Context context){

        return MainViewModel.userRepository.getUser(context);
    }
}
