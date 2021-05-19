package com.example.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserModel... users);

    @Query("select * from users")
    List<UserModel> getAllUsers();

    @Query("select * from users where id like :id")
    UserModel getUser(int id);

    @Update
    void updateUser(UserModel... user);

    @Delete
    void deleteUser(UserModel... user);
}
