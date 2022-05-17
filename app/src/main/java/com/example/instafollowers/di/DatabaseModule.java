package com.example.instafollowers.di;


import android.content.Context;

import com.example.instafollowers.database.UserDatabase;
import com.example.instafollowers.database.userdata.UserDao;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public interface DatabaseModule {

    @Provides
    static UserDao provideUserDao(@ApplicationContext Context context){
        return UserDatabase.getInstance(context).userDao();
    }
}
