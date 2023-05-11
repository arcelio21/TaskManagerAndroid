package com.dev6am.todo.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dev6am.todo.activity.TaskFragment;

import java.util.ArrayList;
import java.util.List;

public class TaskViewPagerAdapter extends FragmentStateAdapter {


    private final List<Fragment> fragments= new ArrayList<>();
    private final List<String> fragmentsString= new ArrayList<>();


    public TaskViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        this.fragmentsString.add("Inicio");
    }

    public void addFragment(Fragment fragment){
        this.fragments.add(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        TaskFragment taskFragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt("position",position);
        taskFragment.setArguments(args);
        Log.println(Log.INFO, "METODO", "createFragment");
        return taskFragment;
    }


    public Fragment getItem(int position){
        TaskFragment taskFragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt("position",position);
        taskFragment.setArguments(args);
        Log.println(Log.INFO, "METODO", "getItem");
        return taskFragment;
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }


}
