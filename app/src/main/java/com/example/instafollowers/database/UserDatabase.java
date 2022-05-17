package com.example.instafollowers.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.instafollowers.database.userdata.User;
import com.example.instafollowers.database.userdata.UserDao;


@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static final String DATABASE_NAME = "insta_followers-app.db";
    private static UserDatabase instance = null;



    public static UserDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (UserDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            UserDatabase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }

        return instance;

    }
}
