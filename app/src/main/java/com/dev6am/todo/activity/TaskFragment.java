package com.dev6am.todo.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev6am.todo.R;

public class TaskFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_taks_category,container,false);
        Log.println(Log.INFO, "METODO", "onCreateView TaskFragment");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        Log.println(Log.INFO, "METODO", "OnVIewCREATED tASKfRAGEMET");
        //TODO CODIGO PARA CAMBIAR INFORMACION SEGUN QUE PESTAÑA ESTE

    }


}
