package com.example.room;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class DeleteTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private DatabaseManager database;
    private TextInputLayout tilDataToBeDeleted;

    DeleteTask setInstance(Context context, DatabaseManager database,
                           TextInputLayout tilDataToBeDeleted) {
        this.context = context;
        this.database = database;
        this.tilDataToBeDeleted = tilDataToBeDeleted;

        return this;
    }

    @Override protected Boolean doInBackground(Void... voids) {
        UserModel userToBeDeleted = null;
        String nameToBeDeleted = tilDataToBeDeleted.getEditText().getText().toString();

        for (int i = 0; i < database.userDao().getAllUsers().size(); i++) {
            if (database.userDao().getAllUsers().get(i).getName().equals(nameToBeDeleted)) {
                userToBeDeleted = database.userDao().getAllUsers().get(i);
                break;
            }
        }

        if(userToBeDeleted != null){
            database.userDao().deleteUser(userToBeDeleted);
            return true;
        }

        return false;
    }

    @Override protected void onPostExecute(Boolean isDeleted) {
        super.onPostExecute(isDeleted);
        if(isDeleted){
            Toast.makeText(context, "Data deleted.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Operation failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
