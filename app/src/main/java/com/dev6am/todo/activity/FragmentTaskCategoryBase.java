package com.dev6am.todo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev6am.todo.R;
import com.dev6am.todo.adapter.TaskAdapter;
import com.dev6am.todo.adapter.TaskViewPagerAdapter;
import com.dev6am.todo.model.Task;
import com.dev6am.todo.util.CheckedTaskListener;
import com.dev6am.todo.util.SelectListener;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTaskCategoryBase#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTaskCategoryBase extends Fragment implements SelectListener, CheckedTaskListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Task> taskList;
    private RecyclerView rvBaseTaskCategory;
    private  TaskAdapter taskAdapter;

    public FragmentTaskCategoryBase() {
        // Required empty public constructor


    }

    //ES OBLIGATORIO CREAR UN CONSTRCUTOR APARTE Y DEJAR EL VACIO
    public FragmentTaskCategoryBase(List<Task> taskList ) {
        this.taskList = taskList;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentTaskCtaegoryBase.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTaskCategoryBase newInstance(List<Task> taskList, TaskViewPagerAdapter taskViewPagerAdapter) {
        FragmentTaskCategoryBase fragment = new FragmentTaskCategoryBase();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            Log.println(Log.INFO,"ON CREATE","DENTRO DEL IF");
        }

        Log.println(Log.INFO,"ON CREATE","FUERA DEL IF");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.println(Log.INFO,"ONCREATEDVIEW","SE INFLO EL FRAGMENT");
        View view = inflater.inflate(R.layout.fragment_task_category_base, container, false);
        rvBaseTaskCategory = view.findViewById(R.id.rvTaskCategoryBase);

         taskAdapter = new TaskAdapter(this.taskList,this, this);

        rvBaseTaskCategory.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvBaseTaskCategory.setAdapter(taskAdapter);

        return view;
    }

    @Nullable
    @Override
    public View getView() {
        Log.println(Log.INFO,"GETVIEW", "AQUI OBTIENE LA VISTA");
        return super.getView();
    }

    @Override
    public void setTaskSelectMoreInfoListener(int position) {

        Intent intent = new Intent(this.getView().getContext(),EditTaskActivity.class);
        intent.putExtra("ID_TASK",taskList.get(position).getId());
        startActivity(intent);

    }


    /**
     * METODO QUE SE ENCARGAD DE REORDENAR LAS TAREAS POR PRIORIDAD Y SI HA SIDO COMPLETADA O NO
     * @param position
     * @param task
     */
    @Override
    public void setCheckeCompleteTask(int position, Task task) {

        this.taskList=this.filterTaskByPriorityAndChecked();
        this.taskAdapter.setTaskList(this.taskList);

        this.rvBaseTaskCategory.post(() -> {
            this.taskAdapter.notifyDataSetChanged();
        });

    }

        private List<Task> filterTaskByPriorityAndChecked(){

        Comparator<Task> comparatorPriority= Comparator.comparing(Task::getPriorityLevel);
        Comparator<Task> comparator = Comparator.comparing(Task::getChecked);

        return this.taskList.stream()
                .sorted(comparator.thenComparing(comparatorPriority))
                .collect(Collectors.toList());
    }
}