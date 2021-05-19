package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnInsertData, btnRetrieveData, btnUpdateData, btnDeleteData;
    TextInputLayout tilInsert, tilDataToBeUpdated, tilUpdatedData, tilDataToBeDeleted;
    private DatabaseManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsertData = findViewById(R.id.btnInsertData);
        btnRetrieveData = findViewById(R.id.btnRetrieveData);
        btnUpdateData = findViewById(R.id.btnUpdateData);
        btnDeleteData = findViewById(R.id.btnDeleteData);

        tilInsert = findViewById(R.id.tilInsert);
        tilDataToBeUpdated = findViewById(R.id.tilDataToBeUpdated);
        tilUpdatedData = findViewById(R.id.tilUpdatedData);
        tilDataToBeDeleted = findViewById(R.id.tilDataToBeDeleted);

        btnInsertData.setOnClickListener(this);
        btnRetrieveData.setOnClickListener(this);
        btnUpdateData.setOnClickListener(this);
        btnDeleteData.setOnClickListener(this);

        database = DatabaseManager.getInstance(this);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInsertData:
                new InsertTask().setInstance(MainActivity.this, database, tilInsert).execute();
                break;

            case R.id.btnRetrieveData:
                new RetrieveTask().setInstance(MainActivity.this, database).execute();
                break;

            case R.id.btnUpdateData:
                new UpdateTask().setInstance(MainActivity.this, database, tilDataToBeUpdated,
                        tilUpdatedData).execute();
                break;

            case R.id.btnDeleteData:
                new DeleteTask().setInstance(MainActivity.this, database, tilDataToBeDeleted).execute();
                break;
        }
    }
}