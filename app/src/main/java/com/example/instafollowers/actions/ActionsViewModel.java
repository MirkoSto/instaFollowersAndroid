package com.example.instafollowers.actions;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.instafollowers.database.userdata.UserRepository;


public class ActionsViewModel extends ViewModel {
    private final SavedStateHandle savedStateHandle;
    private final UserRepository userRepository;

    @ViewModelInject
    public ActionsViewModel(
            UserRepository userRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.userRepository = userRepository;
        this.savedStateHandle = savedStateHandle;

    }

}
