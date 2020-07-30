package com.example.mvptodo;

public interface BasePresenter<T extends BaseView> {
    void onAttach(T view);

    void onDetach();
}
