package com.example.mvptodo.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mvptodo.R;
import com.example.mvptodo.main.MainActivity;
import com.example.mvptodo.model.AppDatabase;
import com.example.mvptodo.model.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


public class TaskDetailActivity extends AppCompatActivity implements TaskDetailContract.View {
    private int                          selectedImportance = Task.IMPORTANCE_NORMAL;
    private ImageView                    lastSelectedImportanceIv;
    private TaskDetailContract.Presenter presenter;
    private EditText                     editText;
    private View                         deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        presenter = new TaskDetailPresenter(AppDatabase.getAppDatabase(this).getTaskDao(),
                (Task) getIntent().getParcelableExtra(MainActivity.KEY_EXTRA_TASK));

        View backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText = findViewById(R.id.taskEt);
        Button saveChangeBtn = findViewById(R.id.saveChangesBtn);
        saveChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveChanges(editText.getText().toString(), selectedImportance);
            }
        });
        deleteBtn = findViewById(R.id.deleteTaskBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteTask();
            }
        });

        View normalImportanceBtn = findViewById(R.id.normalImportanceBtn);
        lastSelectedImportanceIv = normalImportanceBtn.findViewById(R.id.normalImportanceCheckIv);

        View highImportanceBtn = findViewById(R.id.highImportanceBtn);
        highImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.IMPORTANCE_HIGH) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.highImportanceCheckIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance       = Task.IMPORTANCE_HIGH;
                    lastSelectedImportanceIv = imageView;
                }
            }
        });

        View lowImportanceBtn = findViewById(R.id.lowImportanceBtn);
        lowImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.IMPORTANCE_LOW) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.lowImportanceCheckIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_LOW;
                    lastSelectedImportanceIv = imageView;
                }
            }
        });

        normalImportanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImportance != Task.IMPORTANCE_NORMAL) {
                    lastSelectedImportanceIv.setImageResource(0);
                    ImageView imageView = v.findViewById(R.id.normalImportanceCheckIv);
                    imageView.setImageResource(R.drawable.ic_check_white_24dp);
                    selectedImportance = Task.IMPORTANCE_NORMAL;
                    lastSelectedImportanceIv = imageView;
                }
            }
        });
        presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @Override
    public void showTask(Task task) {
        editText.setText(task.getTitle());
        switch (task.getImportance()) {
            case Task.IMPORTANCE_HIGH:
                findViewById(R.id.highImportanceBtn).performClick();
                break;
            case Task.IMPORTANCE_NORMAL:
                findViewById(R.id.normalImportanceBtn).performClick();
                break;
            case Task.IMPORTANCE_LOW:
                findViewById(R.id.lowImportanceBtn).performClick();
                break;
        }
    }

    @Override
    public void setVisibilityDeleteButton(boolean isVisible) {
        deleteBtn.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(findViewById(R.id.root_task_detail), error, BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    @Override
    public void returnResult(Task task, int resultCode) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.KEY_EXTRA_TASK, task);
        setResult(resultCode, intent);
        finish();
    }
}
