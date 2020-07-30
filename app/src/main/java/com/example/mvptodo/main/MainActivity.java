package com.example.mvptodo.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mvptodo.R;
import com.example.mvptodo.model.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
    }

    @Override
    public void showTask(List<Task> tasks) {

    }

    @Override
    public void clearTasks() {

    }

    @Override
    public void updateTasks() {

    }

    @Override
    public void addTasks() {

    }

    @Override
    public void deleteTasks() {

    }

    @Override
    public void setEmptyStateVisibility(boolean isVisible) {

    }
}