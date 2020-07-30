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
        this.taskDao = taskDao; this.tasks = taskDao.getAll();
    }

    @Override
    public void onClickDeleteAllBtn() {

    }

    @Override
    public List<Task> onSearch(String s) {
        return null;
    }

    @Override
    public void onClickTaskItem() {

    }

    @Override
    public void onAttach(@NonNull MainContract.View view) {
        this.view = view; if (!tasks.isEmpty()) {
            view.showTask(tasks); view.setEmptyStateVisibility(false);
        }
        else { view.setEmptyStateVisibility(true); }
    }

    @Override
    public void onDetach() {

    }
}
