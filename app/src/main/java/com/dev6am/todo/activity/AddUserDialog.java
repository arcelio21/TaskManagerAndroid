package com.dev6am.todo.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dev6am.todo.R;

import java.util.Objects;

public class AddUserDialog  extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();


        builder.setView(inflater.inflate(R.layout.dialogstart, null));
        builder.setPositiveButton(R.string.btnAddUser, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TextView editTextuser = getDialog().findViewById(R.id.txtAddUser);
                String userAdd = editTextuser.getText().toString();
                Log.println(Log.INFO, "IMPORTANT", userAdd);
            }
        });
        builder.setNegativeButton(R.string.btnCancelledAddUser, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddUserDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
