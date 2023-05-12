package com.dev6am.todo.util;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class PageChangeTaskCategory extends ViewPager2.OnPageChangeCallback{

    private TabLayout tabLayout;

    public PageChangeTaskCategory(TabLayout tab) {

        this.tabLayout = tab;
    }

    /**
     * DE LOS TABS QUE TIENE EL TABLYOUT, SELECCIONA EL TAB BASANDO EN LA POSIVION DEL VIEWPAGER
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        this.tabLayout.selectTab(this.tabLayout.getTabAt(position));
    }
}
