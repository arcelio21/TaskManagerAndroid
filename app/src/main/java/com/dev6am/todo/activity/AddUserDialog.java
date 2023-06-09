package com.dev6am.todo.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dev6am.todo.R;
import com.dev6am.todo.model.User;
import com.dev6am.todo.repository.UserRepository;

public class AddUserDialog  extends DialogFragment {

    private UserRepository userRepository;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        userRepository = new UserRepository();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();


        builder.setView(inflater.inflate(R.layout.dialogstart, null));

        builder.setPositiveButton(R.string.btnAddUser, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                TextView editTextuser = getDialog().findViewById(R.id.txtAddUser);
                String userAdd = editTextuser.getText().toString();

                User user = User.builder()
                        .userName(userAdd)
                        .build();

                userRepository.saveUserJson(user, requireContext());

                updateNameUser();

                dismiss();
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


    /**
     * Se encargara de actualizar el nombre de usuario que se agregue por primera vez o caso de
     * que se quiera cambiar
     */
    private void updateNameUser(){
        MainActivity activity= (MainActivity) getActivity();
        Button button =activity.findViewById(R.id.btnAdd);

        User user=userRepository.getUser(requireContext());

        button.setText(user.getUserName());
    }
}
