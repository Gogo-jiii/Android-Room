package com.example.room;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

public class RetrieveTask extends AsyncTask<Void, Void, List<UserModel>> {

    private Context context;
    private DatabaseManager database;

    RetrieveTask setInstance(Context context, DatabaseManager database) {
        this.context = context;
        this.database = database;

        return this;
    }

    @Override protected List<UserModel> doInBackground(Void... voids) {
        List<UserModel> allUsers = database.userDao().getAllUsers();
        return allUsers;
    }

    @Override protected void onPostExecute(List<UserModel> allUsers) {
        super.onPostExecute(allUsers);
        Toast.makeText(context, allUsers.toString(),
                Toast.LENGTH_SHORT).show();
    }
}
