package com.dev6am.todo.repository;

import android.content.Context;

import com.dev6am.todo.model.Data;
import com.dev6am.todo.model.Task;
import com.dev6am.todo.util.ConverterJson;
import com.dev6am.todo.util.ReaderWriterFIle;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static final String FILE_NAME = "Tasks.json";

    public boolean saveTask(Task task, Context context){

        List<Task> taskList = new ArrayList<>();

        taskList.add(task);


        //VALIDAR SI EXISTE YA TAREAS CREADAS
        String taksJson = ReaderWriterFIle.readFileJson(context.getFilesDir().getAbsolutePath(),TaskRepository.FILE_NAME);

        if( taksJson!=null && !taksJson.trim().equals("")){
            taskList = ConverterJson.jsonToDataList(taksJson,Task[].class);
            taskList.add(task);
        }

        Data data = Data.builder()
                .data(taskList)
                .build();

        String dataJson = ConverterJson.DataToJson(data);


        return ReaderWriterFIle.writeFileJson(dataJson,context.getFilesDir().getAbsolutePath(), TaskRepository.FILE_NAME);
    }

    public List<Task> getTasks(Context context){

        String dataJson= ReaderWriterFIle.readFileJson(context.getFilesDir().getAbsolutePath(),TaskRepository.FILE_NAME);

        return ConverterJson.jsonToDataList(dataJson,Task[].class);
    }
}
