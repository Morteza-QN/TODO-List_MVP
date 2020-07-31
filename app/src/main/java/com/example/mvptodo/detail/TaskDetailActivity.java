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


public class TaskDetailActivity extends AppCompatActivity implements TaskDetailContract.View {
    private int                          selectedImportance = Task.IMPORTANCE_NORMAL;
    private ImageView                    lastSelectedImportanceIv;
    private TaskDetailContract.Presenter presenter;
    private EditText                     editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_edit_task);

        init();

        presenter = new TaskDetailPresenter(AppDatabase.getAppDatabase(this).getTaskDao()); presenter.onAttach(this);

        editText = findViewById(R.id.taskEt); Button saveChangeBtn = findViewById(R.id.saveChangesBtn);
        saveChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveChanges(editText.getText().toString(), selectedImportance);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy(); presenter.onDetach();
    }

    private void init() {
        View backBtn = findViewById(R.id.backBtn); backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                    imageView.setImageResource(R.drawable.ic_check_white_24dp); selectedImportance       =
Task.IMPORTANCE_HIGH; lastSelectedImportanceIv                                                           = imageView;
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
                    imageView.setImageResource(R.drawable.ic_check_white_24dp); selectedImportance = Task.IMPORTANCE_LOW;

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
                    imageView.setImageResource(R.drawable.ic_check_white_24dp); selectedImportance = Task.IMPORTANCE_NORMAL;

                    lastSelectedImportanceIv = imageView;
                }
            }
        });
    }

    @Override
    public void showTask(Task task) {

    }

    @Override
    public void setVisibilityDeleteButton(boolean isVisible) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void returnResult(Task task, int resultCode) {
        Intent intent = new Intent(); intent.putExtra(MainActivity.KEY_EXTRA_TASK, task); setResult(resultCode, intent);
        finish();
    }
}
