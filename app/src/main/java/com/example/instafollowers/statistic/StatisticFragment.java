package com.example.instafollowers.statistic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instafollowers.MainActivity;
import com.example.instafollowers.R;
import com.example.instafollowers.databinding.FragmentActionsBinding;
import com.example.instafollowers.databinding.FragmentStatisticBinding;
import com.example.instafollowers.homepage.UserViewModel;

import dagger.hilt.android.AndroidEntryPoint;



@AndroidEntryPoint
public class StatisticFragment extends Fragment {
    private FragmentStatisticBinding binding;
    private MainActivity mainActivity;
    private UserViewModel viewModel;

    public StatisticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        viewModel = new ViewModelProvider(mainActivity).get(UserViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatisticBinding.inflate(inflater, container, false);

        binding.followersViaApp.setText("Followers: " + viewModel.getFollowers_via_app());
        binding.followersPercentage.setText("Followers percentage: " + viewModel.getFollowers_percentage());

        binding.followedByApp.setText("Total followed: " + viewModel.getTotal_followed());
        binding.unfollowedByApp.setText("Total unfollowed: " + viewModel.getTotal_unfollowed());
        binding.likes.setText("Total likes: " + viewModel.getTotal_liked());
        binding.comments.setText("Total comments: " + viewModel.getTotal_commented());
        binding.stories.setText("Total stories: " + viewModel.getTotal_stories());

        return binding.getRoot();
    }
}