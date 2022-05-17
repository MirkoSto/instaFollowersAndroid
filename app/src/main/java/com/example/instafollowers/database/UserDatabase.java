package com.example.instafollowers.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;


@androidx.room.Database(entities = {}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "run-app.db";
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
