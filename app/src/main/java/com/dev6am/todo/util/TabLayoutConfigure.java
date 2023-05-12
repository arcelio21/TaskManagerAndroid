package com.dev6am.todo.util;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.dev6am.todo.model.Category;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class TabLayoutConfigure implements TabLayoutMediator.TabConfigurationStrategy {


    private List<Category> categories;

    public TabLayoutConfigure(List<Category> categories) {
        this.categories = categories;

    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

         tab.setText(categories.get(position).getName());
    }
}
