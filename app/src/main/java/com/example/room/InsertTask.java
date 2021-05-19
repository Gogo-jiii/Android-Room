package com.example.room;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class InsertTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private DatabaseManager database;
    private TextInputLayout tilInsert;

    InsertTask setInstance(Context context, DatabaseManager database, TextInputLayout tilInsert) {
        this.context = context;
        this.database = database;
        this.tilInsert = tilInsert;

        return this;
    }

    @Override protected Boolean doInBackground(Void... voids) {
        String name = tilInsert.getEditText().getText().toString();
        database.userDao().insert(new UserModel(name));

        for (int i = 0; i < database.userDao().getAllUsers().size(); i++) {
            if (database.userDao().getAllUsers().get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override protected void onPostExecute(Boolean isInserted) {
        super.onPostExecute(isInserted);
        if(isInserted){
            Toast.makeText(context, "Data inserted.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Operation failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
