package com.dev6am.todo.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dev6am.todo.R;
import com.dev6am.todo.model.User;
import com.dev6am.todo.repository.UserRepository;
import com.dev6am.todo.util.UpdateListenerDate;

import java.time.LocalDate;

public class DialogDatePicker extends DialogFragment {

    private UpdateListenerDate updateListenerDate;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        DatePicker view =(DatePicker) inflater.inflate(R.layout.datepicker_dialog,null);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if(this.updateListenerDate instanceof  EditTaskActivity){

                String dateTaks= ((EditTaskActivity) this.updateListenerDate).getDateTask();
                LocalDate localDate = LocalDate.parse(dateTaks);
                view.init(localDate.getYear(),localDate.getMonthValue()-1,localDate.getDayOfMonth(),null);
            }

            view.setOnDateChangedListener((datePicker, i, i1, i2) -> {

                LocalDate dateSelect = LocalDate.of(
                        view.getYear(),
                        view.getMonth()+1,
                        view.getDayOfMonth()
                );

                this.updateListenerDate.updateDate(dateSelect.toString());
                dismiss();
            });


        }
        builder.setView(view);

        return builder.create();
    }

    public void setUpdateListenerDate(UpdateListenerDate updateListenerDate) {
        this.updateListenerDate = updateListenerDate;
    }
}


