package com.example.mvptodo.detail;

import com.example.mvptodo.BasePresenter;
import com.example.mvptodo.BaseView;
import com.example.mvptodo.model.Task;

public interface TaskDetailContract {

    interface View extends BaseView {
        void showTask(Task task);

        void setVisibilityDeleteButton(boolean isVisible);
    }

    interface Presenter extends BasePresenter<View> {
        void deleteTask(Task task);

        void saveChanges(int important, String title);
    }
}
