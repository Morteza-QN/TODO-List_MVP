package com.example.mvptodo.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvptodo.R;
import com.example.mvptodo.detail.TaskDetailActivity;
import com.example.mvptodo.model.AppDatabase;
import com.example.mvptodo.model.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, TaskAdapter.TaskItemEventListener {
    public static final  String                 KEY_EXTRA_TASK          = "EXTRA_TASK";
    public static final  int                    RESULT_CODE_ADD_TASK    = 770;
    public static final  int                    RESULT_CODE_DELETE_TASK = 771;
    public static final  int                    RESULT_CODE_UPDATE_TASK = 772;
    private static final int                    REQUEST_CODE            = 599;
    private              MainContract.Presenter presenter;
    private              TaskAdapter            adapter;
    private              View                   emptyState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(AppDatabase.getAppDatabase(this).getTaskDao());
        adapter   = new TaskAdapter(this, this);

        emptyState = findViewById(R.id.emptyState);
        RecyclerView recyclerView = findViewById(R.id.taskListRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);



        View addNewTaskBtn = findViewById(R.id.addNewTaskBtn);
        addNewTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, TaskDetailActivity.class), REQUEST_CODE);
            }
        });

        View deleteAllBtn = findViewById(R.id.deleteAllBtn);
        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { presenter.onClickDeleteAllBtn(); }
        });

        EditText searchEt = findViewById(R.id.searchEt);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public void showTask(List<Task> tasks) {
        adapter.setTasks(tasks);
    }

    @Override
    public void clearTasks() {
        adapter.clearItems();
    }

    @Override
    public void updateTasks(Task task) {
        adapter.updateItem(task);
    }

    @Override
    public void setEmptyStateVisibility(boolean isVisible) {
        emptyState.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            Task task = data.getParcelableExtra(KEY_EXTRA_TASK);
            if (task == null) {return;}
            switch (resultCode) {
                case RESULT_CODE_ADD_TASK:
                    adapter.addItem(task);
                    break;
                case RESULT_CODE_DELETE_TASK:
                    adapter.deleteItem(task);
                    break;
                case RESULT_CODE_UPDATE_TASK:
                    adapter.updateItem(task);
                    break;
            }
            setEmptyStateVisibility(adapter.getItemCount() == 0);
        }
    }

    @Override
    public void onClickItem(Task task) {
        presenter.onClickTaskItem(task);
    }

    @Override
    public void onLongClickItem(Task task) {
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra(KEY_EXTRA_TASK, task);
        startActivityForResult(intent, REQUEST_CODE);
    }
}