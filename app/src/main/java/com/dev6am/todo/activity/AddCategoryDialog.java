package com.dev6am.todo.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dev6am.todo.R;
import com.dev6am.todo.model.Category;
import com.dev6am.todo.repository.CategoryRepository;
import com.dev6am.todo.util.DialogCategoryListener;
import com.dev6am.todo.util.GeneratorIdCategory;

public class AddCategoryDialog extends DialogFragment {

    private final MainActivity mainActivity;

    public AddCategoryDialog(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.mainActivity);
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View viewDialog = inflater.inflate(R.layout.dialog_add_category,null);
        builder.setView(viewDialog);

        Button btnAddCategory = viewDialog.findViewById(R.id.btnAddCategory);



        btnAddCategory.setOnClickListener(view -> {

            TextView txtNameCategory = (TextView) viewDialog.findViewById(R.id.editTNameCategory);

            Category category = Category.builder()
                    .id(GeneratorIdCategory.getGeneratorIdCategory().incrementAndGet())
                    .name(txtNameCategory.getText().toString())
                    .build();
            mainActivity.updateCategory(category);
            dismiss();
        });
        return builder.create();
    }
}
