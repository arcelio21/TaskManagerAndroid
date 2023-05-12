package com.dev6am.todo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev6am.todo.R;
import com.dev6am.todo.model.SubTask;
import com.dev6am.todo.util.SubTaskListener;

import java.util.List;

/**
 * CLASE QUE SE ENCARGAR DE MOSTRAR SUBTAREAS DE UNA TAREA PRINCIPAL EN RECYCLE VIEW
 */
public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder> {

    private List<SubTask> subTaskList;
    private SubTaskListener subTaskListener;

    public SubTaskAdapter(List<SubTask> subTaskList, SubTaskListener subTaskListener) {

        this.subTaskList=subTaskList;
        this.subTaskListener = subTaskListener;
    }

    @NonNull
    @Override
    public SubTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_subtask,parent,false);
        return new SubTaskViewHolder(view, this.subTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTaskViewHolder holder, int position) {

        holder.SetData(this.subTaskList.get(position));

    }

    @Override
    public int getItemCount() {
        return this.subTaskList.size();
    }





    public class SubTaskViewHolder extends RecyclerView.ViewHolder{

        private SubTask subTask;
        private final CheckBox chkSubTask;
        private Button btnDelete;
        private SubTaskListener subTaskListener;

        public SubTaskViewHolder(@NonNull View itemView,SubTaskListener subTaskListener) {
            super(itemView);

            this.chkSubTask=itemView.findViewById(R.id.ckbSubTask);
            this.btnDelete = itemView.findViewById(R.id.btnDeletSubTask);
            this.subTaskListener = subTaskListener;

        }


        public void SetData(SubTask subTask){
            this.subTask=subTask;

            this.chkSubTask.setText(subTask.getName());
            this.chkSubTask.setChecked(subTask.isChecked());

            this.chkSubTask.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                subTask.setChecked(isChecked);
                if(isChecked){
                    this.chkSubTask.setBackgroundColor(chkSubTask.getContext().getColor(R.color.purple_500));
                }else {
                    this.chkSubTask.setBackgroundColor(chkSubTask.getContext().getColor(R.color.white));
                }
            });

            this.btnDelete.setOnClickListener(view -> {
                this.subTaskListener.remove(this.subTask);
            });
        }



    }

    public void setSubTaskList(List<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
    }

}
