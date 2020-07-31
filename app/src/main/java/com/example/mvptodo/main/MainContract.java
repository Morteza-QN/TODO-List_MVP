package com.example.mvptodo.main;

import com.example.mvptodo.BasePresenter;
import com.example.mvptodo.BaseView;
import com.example.mvptodo.model.Task;

import java.util.List;

interface MainContract {

    interface View extends BaseView {
        void showTask(List<Task> tasks);

        void clearTasks();

        void updateTasks(Task task);

        void setEmptyStateVisibility(boolean isVisible);
    }

    interface Presenter extends BasePresenter<View> {
        void onClickDeleteAllBtn();

        void onSearch(String s);

        void onClickTaskItem(Task task);
    }
}
