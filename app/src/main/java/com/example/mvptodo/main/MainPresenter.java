package com.example.mvptodo.main;

import androidx.annotation.NonNull;

import com.example.mvptodo.model.Task;
import com.example.mvptodo.model.TaskDao;

import java.util.List;

class MainPresenter implements MainContract.Presenter {
    private TaskDao           taskDao;
    private List<Task>        tasks;
    private MainContract.View view;

    public MainPresenter(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.tasks   = taskDao.getAll();
    }

    @Override
    public void onClickDeleteAllBtn() {
        taskDao.deleteAll();
        view.clearTasks();
        view.setEmptyStateVisibility(true);
    }

    @Override
    public void onSearch(String s) {
        List<Task> tasks = s.isEmpty() ? taskDao.getAll() : taskDao.search(s);
        view.showTask(tasks);

    }

    @Override
    public void onClickTaskItem(@NonNull Task task) {
        task.setCompleted(!task.isCompleted());
        int res = taskDao.update(task);
        if (res > 0) {
            view.updateTasks(task);
        }
    }

    @Override
    public void onAttach(@NonNull MainContract.View view) {
        this.view = view;
        if (!tasks.isEmpty()) {
            view.showTask(tasks);
            view.setEmptyStateVisibility(false);
        }
        else { view.setEmptyStateVisibility(true); }
    }

    @Override
    public void onDetach() {
        taskDao = null;
        tasks   = null;
        view    = null;
    }
}
