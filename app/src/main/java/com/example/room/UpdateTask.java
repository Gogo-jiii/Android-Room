package com.example.room;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class UpdateTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private DatabaseManager database;
    private TextInputLayout tilDataToBeUpdated, tilUpdatedData;

    UpdateTask setInstance(Context context, DatabaseManager database,
                           TextInputLayout tilDataToBeUpdated, TextInputLayout tilUpdatedData) {
        this.context = context;
        this.database = database;
        this.tilDataToBeUpdated = tilDataToBeUpdated;
        this.tilUpdatedData = tilUpdatedData;

        return this;
    }

    @Override protected Boolean doInBackground(Void... voids) {
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
            return true;
        }

        return false;
    }

    @Override protected void onPostExecute(Boolean isUpdated) {
        super.onPostExecute(isUpdated);
        if (isUpdated) {
            Toast.makeText(context, "Data updated.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Operation failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
