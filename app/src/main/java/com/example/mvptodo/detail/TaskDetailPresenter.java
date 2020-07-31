package com.example.mvptodo.detail;

import com.example.mvptodo.main.MainActivity;
import com.example.mvptodo.model.Task;
import com.example.mvptodo.model.TaskDao;

class TaskDetailPresenter implements TaskDetailContract.Presenter {
    private TaskDao                 taskDao;
    private TaskDetailContract.View view;

    public TaskDetailPresenter(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void deleteTask(Task task) {

    }

    @Override
    public void saveChanges(String title, int important) {

        if (title.isEmpty()) {
            view.showError("Enter task title"); return;
        }

        Task task = new Task(title.trim(), important); long id = taskDao.add(task); task.setId(id);
        view.returnResult(task, MainActivity.RESULT_CODE_ADD_TASK);
    }

    @Override
    public void onAttach(TaskDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {

    }
}
