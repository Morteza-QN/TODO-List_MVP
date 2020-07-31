package com.example.mvptodo.detail;

import com.example.mvptodo.BasePresenter;
import com.example.mvptodo.BaseView;
import com.example.mvptodo.model.Task;

public interface TaskDetailContract {

    interface View extends BaseView {
        void showTask(Task task);

        void setVisibilityDeleteButton(boolean isVisible);

        void showError(String error);

        void returnResult(Task task, int resultCode);
    }

    interface Presenter extends BasePresenter<View> {
        void deleteTask();

        void saveChanges(String title, int important);
    }
}
