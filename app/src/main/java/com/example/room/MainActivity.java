package com.example.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnInsertData, btnRetrieveData, btnUpdateData, btnDeleteData;
    TextInputLayout tilInsert, tilDataToBeUpdated, tilUpdatedData, tilDataToBeDeleted;
    private DatabaseManager database;
    private ExecutorService executorService;
    private Handler handler;

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
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInsertData:
                insert();
                //new InsertTask().setInstance(MainActivity.this, database, tilInsert).execute();
                break;

            case R.id.btnRetrieveData:
                retrieve();
                //new RetrieveTask().setInstance(MainActivity.this, database).execute();
                break;

            case R.id.btnUpdateData:
                update();
                //new UpdateTask().setInstance(MainActivity.this, database, tilDataToBeUpdated,
                //  tilUpdatedData).execute();
                break;

            case R.id.btnDeleteData:
                delete();
                //new DeleteTask().setInstance(MainActivity.this, database, tilDataToBeDeleted)
                // .execute();
                break;
        }
    }

    private void delete() {
        final boolean[] isDeleted = {false};

        executorService.execute(new Runnable() {
            @Override public void run() {
                //task
                UserModel userToBeDeleted = null;
                String nameToBeDeleted = tilDataToBeDeleted.getEditText().getText().toString();

                for (int i = 0; i < database.userDao().getAllUsers().size(); i++) {
                    if (database.userDao().getAllUsers().get(i).getName().equals(nameToBeDeleted)) {
                        userToBeDeleted = database.userDao().getAllUsers().get(i);
                        break;
                    }
                }

                if (userToBeDeleted != null) {
                    database.userDao().deleteUser(userToBeDeleted);
                    isDeleted[0] = true;
                }

                handler.post(new Runnable() {
                    @Override public void run() {
                        //update ui
                        if (isDeleted[0]) {
                            Toast.makeText(MainActivity.this, "Data deleted.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Operation failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void update() {
        final boolean[] isUpdated = {false};

        executorService.execute(new Runnable() {
            @Override public void run() {
                //task
                String oldName = tilDataToBeUpdated.getEditText().getText().toString();
                String newName = tilUpdatedData.getEditText().getText().toString();
                int id = -1;

                for (int i = 0; i < database.userDao().getAllUsers().size(); i++) {
                    if (database.userDao().getAllUsers().get(i).getName().equals(oldName)) {
                        id = database.userDao().getAllUsers().get(i).getId();
                        break;
                    }
                }

                UserModel userModel = database.userDao().getUser(id);
                if (userModel != null) {
                    userModel.setName(newName);
                    database.userDao().updateUser(userModel);
                    isUpdated[0] = true;
                }

                handler.post(new Runnable() {
                    @Override public void run() {
                        //update ui
                        if (isUpdated[0]) {
                            Toast.makeText(MainActivity.this, "Data updated.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Operation failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void retrieve() {
        executorService.execute(new Runnable() {
            @Override public void run() {
                //task
                List<UserModel> allUsers = database.userDao().getAllUsers();

                handler.post(new Runnable() {
                    @Override public void run() {
                        //update ui
                        Toast.makeText(MainActivity.this, allUsers.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void insert() {
        final boolean[] isInserted = {false};

        executorService.execute(new Runnable() {
            @Override public void run() {
                //task
                String name = tilInsert.getEditText().getText().toString();
                database.userDao().insert(new UserModel(name));

                for (int i = 0; i < database.userDao().getAllUsers().size(); i++) {
                    if (database.userDao().getAllUsers().get(i).getName().equals(name)) {
                        isInserted[0] = true;
                    }
                }


                handler.post(new Runnable() {
                    @Override public void run() {
                        if (isInserted[0]) {
                            Toast.makeText(MainActivity.this, "Data inserted.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Operation failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}