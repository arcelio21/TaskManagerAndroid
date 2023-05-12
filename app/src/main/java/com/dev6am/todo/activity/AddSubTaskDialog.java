package com.dev6am.todo.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dev6am.todo.R;
import com.dev6am.todo.model.SubTask;
import com.dev6am.todo.util.DialogListener;
import com.dev6am.todo.util.GeneratorIDSubTask;
import com.dev6am.todo.util.GeneratorIDTask;
import com.google.android.material.textfield.TextInputEditText;

public class AddSubTaskDialog extends DialogFragment {

    private String nameSubTask;
    private DialogListener dialogListener;

    /**
     * SE OBTIENE REFERENCIA DEL CONTEXTO DONDE SE MOSTRARA, SE LLAMA AUTOMATICAMENTE
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialogListener = (DialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View viewDialog = inflater.inflate(R.layout.dialog_add_subtask,null);

        builder.setView(viewDialog);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                TextView txtName = viewDialog.findViewById(R.id.txtNameSubTask);

                nameSubTask=txtName.getText().toString();
                Long idSubTask= GeneratorIDSubTask.getGeneratorIdSubTask().incrementAndGet();

                SubTask sub= new SubTask();
                sub.setId(idSubTask);
                sub.setName(nameSubTask);
                sub.setChecked(false);
                Log.d("DIALOG","CREO LA TAREA");


                dialogListener.setDataSubTask(sub);

                dismiss();
            }
        }).setNegativeButton("cancelSave", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddSubTaskDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

}
