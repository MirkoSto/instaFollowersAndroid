package com.example.instafollowers.database.userdata;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class UserRepository {
    private final ExecutorService executorService;
    private final UserDao userDao;


    @Inject
    public UserRepository(
            ExecutorService executorService,
            UserDao userDao) {
        this.executorService = executorService;
        this.userDao = userDao;
    }
}
