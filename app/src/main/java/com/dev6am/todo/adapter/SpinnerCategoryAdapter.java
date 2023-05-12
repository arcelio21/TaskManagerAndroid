package com.dev6am.todo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dev6am.todo.R;
import com.dev6am.todo.model.Category;

import java.util.List;

public class SpinnerCategoryAdapter extends ArrayAdapter<Category> {


    private List<Category> categories;

    public SpinnerCategoryAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Category> objects) {
        super(context, resource, textViewResourceId, objects);

        this.categories=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        TextView txtNameCategory=view.findViewById(R.id.txtNameCategory);
        txtNameCategory.setText(this.categories.get(position).getName());
        //txtNameCategory.setBackgroundColor(this.categories.get(position).getColor());
        //txtNameCategory.setTextColor(parent.getResources().getColor(R.color.white,parent.getContext().getTheme()));

        return  view;
    }
}

