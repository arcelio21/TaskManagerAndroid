package com.dev6am.todo.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev6am.todo.R;
import com.dev6am.todo.activity.MainActivity;
import com.dev6am.todo.model.Task;
import com.dev6am.todo.util.CheckedTaskListener;
import com.dev6am.todo.util.SelectListener;

import java.util.List;

/**
 * CLASE ADAPTER PARA MOSTRAR TAREAS DISPONIBLES
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private SelectListener listenerBtn;
    private CheckedTaskListener checkedTaskListener;

    private  List<Task> taskList;

    public TaskAdapter(List<Task> taskList, SelectListener listener, CheckedTaskListener checkedTaskListener) {
        this.taskList = taskList;
        this.listenerBtn = listener;
        this.checkedTaskListener = checkedTaskListener;
    }

    /**
     * SE OBTIENE EL LAYOUT DEFINIDO PARA LOS ELEMENTOS, Y SE LE DEFINER AL HOLDER
     * PARA QUE A PARTIR DE ESTE LAYOUT SE CREA LA CANTIDAD SEGUN EL TAMAÑO DE LA LISTA ASGINADA AL ADAPTER
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_taks,parent,false);
        return new TaskViewHolder(view);
    }

    /**
     * ASIGNAR DATA A UN INTERFAZ DE ELEMENTO
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.setTask(this.taskList.get(position));

        //PARA OBTENER LA POSICION DEL OBJETO DEL QUE SE QUIERE VER MAS INFORMACION
        holder.getBtnSeeInfo().setOnClickListener(view -> {

            if (this.listenerBtn!=null){
                this.listenerBtn.setTaskSelectMoreInfoListener(position);
            }
        });

        holder.getCkbTaskCheck().setOnCheckedChangeListener((compoundButton, b) -> {

            if(this.checkedTaskListener!=null){
                this.taskList.get(position).setChecked(compoundButton.isChecked());
                this.checkedTaskListener.setCheckeCompleteTask(position, this.taskList.get(position));
                Log.d("FUNCIONA EL CHECKED", "ORDENO 1");
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        private Task task;
        private final CheckBox ckbTaskCheck;
        private final TextView txtNameTask;
        private final TextView txtPriorityName;
        private final TextView txtDateTask;
        private final Button btnSeeInfo;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            this.ckbTaskCheck = itemView.findViewById(R.id.ckbTaskCheck);
            this.txtNameTask = itemView.findViewById(R.id.txtNameTaskLayoutTask);
            this.txtPriorityName = itemView.findViewById(R.id.txtNamePriorityLayoutTask);
            this.txtDateTask = itemView.findViewById(R.id.txtDateLayoutTask);
            this.btnSeeInfo = itemView.findViewById(R.id.btnShowInfoTask);

        }

        public void setTask(Task task) {
            this.task = task;

            this.ckbTaskCheck.setChecked(task.getChecked());

            this.txtNameTask.setText(task.getTitle());
            this.txtPriorityName.setText(task.getPriorityLevel().name());
            this.txtDateTask.setText(task.getDate());

        }

        public Button getBtnSeeInfo() {
            return btnSeeInfo;
        }

        public CheckBox getCkbTaskCheck() {
            return ckbTaskCheck;
        }
    }

    public void setListenerBtn(SelectListener listenerBtn) {
        this.listenerBtn = listenerBtn;
    }

    public void setCheckedTaskListener(CheckedTaskListener checkedTaskListener) {
        this.checkedTaskListener = checkedTaskListener;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
