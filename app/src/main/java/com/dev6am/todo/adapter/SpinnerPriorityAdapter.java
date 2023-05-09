package com.dev6am.todo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dev6am.todo.R;
import com.dev6am.todo.model.PriorityLevel;

import java.util.List;

public class SpinnerPriorityAdapter extends ArrayAdapter<PriorityLevel> {

    private List<PriorityLevel> priorityLevels;



    public SpinnerPriorityAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<PriorityLevel> objects) {
        super(context, resource, textViewResourceId, objects);
        this.priorityLevels = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = super.getView(position, convertView, parent);
        TextView txtNameAdapter = view.findViewById(R.id.txtNamePriority);
        txtNameAdapter.setText(this.priorityLevels.get(position).name());

        return view;
    }

}
