package com.example.instafollowers.database.userdata;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void createUser(User user);

    @Query("Select tags from User")
    LiveData<String[]> getUserData();

}
