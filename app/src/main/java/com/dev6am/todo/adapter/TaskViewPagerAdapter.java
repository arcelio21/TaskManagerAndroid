package com.dev6am.todo.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dev6am.todo.activity.FragmentTaskCategoryBase;
import com.dev6am.todo.model.Category;
import com.dev6am.todo.model.PriorityLevel;
import com.dev6am.todo.model.Task;
import com.dev6am.todo.util.CheckedTaskListener;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskViewPagerAdapter extends FragmentStateAdapter {


    private List<Task> taskList;
    private List<Category> categories;

    public TaskViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,
                                List<Task> tasks, List<Category> categories) {
        super(fragmentManager, lifecycle);

        this.taskList = tasks;
        this.categories = categories;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Log.d("TASKVIEWPAGEADAPTER","se crea fragment en taskViewAdapter, position: "+position);
        return new FragmentTaskCategoryBase(this.filterTaskByCategory(this.taskList,
                this.categories.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }

    private List<Task> filterTaskByCategory(List<Task> tasks, Long idCategory){

        Predicate<Task> idCategoryPredicate = (task)-> task.getTags().getId().equals(idCategory);

        Comparator<Task> comparatorPriority= Comparator.comparing(Task::getPriorityLevel);

        return tasks.stream()
                .filter(idCategoryPredicate)
                .sorted(comparatorPriority)
                .sorted(Comparator.comparing(Task::getChecked))
                .collect(Collectors.toList());
    }


}
