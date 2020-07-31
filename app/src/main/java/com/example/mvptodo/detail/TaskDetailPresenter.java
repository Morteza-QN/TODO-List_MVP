package com.example.mvptodo.detail;

import com.example.mvptodo.main.MainActivity;
import com.example.mvptodo.model.Task;
import com.example.mvptodo.model.TaskDao;

class TaskDetailPresenter implements TaskDetailContract.Presenter {
    private TaskDao                 taskDao;
    private TaskDetailContract.View view;
    private Task                    task;

    public TaskDetailPresenter(TaskDao taskDao, Task task) {
        this.taskDao = taskDao;
        this.task    = task;
    }

    @Override
    public void deleteTask() {
        if (task == null) {return;}
        int res = taskDao.delete(task);
        if (res > 0) {
            view.returnResult(task, MainActivity.RESULT_CODE_DELETE_TASK);
        }
    }

    @Override
    public void saveChanges(String title, int important) {

        if (title.isEmpty()) {
            view.showError("Enter task title");
            return;
        }
        if (task == null) {
            Task task = new Task(title.trim(), important);
            long id   = taskDao.add(task);
            task.setId(id);
            view.returnResult(task, MainActivity.RESULT_CODE_ADD_TASK);
        }
        else {
            task.setTitle(title.trim());
            task.setImportance(important);
            int res = taskDao.update(task);
            if (res > 0) {
                view.returnResult(task, MainActivity.RESULT_CODE_UPDATE_TASK);
            }
        }
    }

    @Override
    public void onAttach(TaskDetailContract.View view) {
        this.view = view;
        if (task != null) {
            view.setVisibilityDeleteButton(true);
            view.showTask(task);
        }
    }

    @Override
    public void onDetach() {
        taskDao = null;
        view    = null;
        task    = null;
    }
}
