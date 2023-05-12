package com.dev6am.todo.util;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class TaskCategoryTabListener implements TabLayout.OnTabSelectedListener {

    private ViewPager2 viewPager2;

    public TaskCategoryTabListener(ViewPager2 viewPager2) {

        this.viewPager2=viewPager2;
    }

    /**
     * AL SELECCIONAR UN TAB AUTOMATICAMENTE EL VIEWPAGER MOSTRAR LA INFORMACION DE LAYOUT CON BASE A LA POSICION
     * DEL TAB
     * @param tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        this.viewPager2.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
