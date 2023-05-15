package com.dev6am.todo.util;

import com.dev6am.todo.model.Task;

public interface CheckedTaskListener {


    void setCheckeCompleteTask(int position, Task task);
}
