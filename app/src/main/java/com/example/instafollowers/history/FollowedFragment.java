package com.example.instafollowers.history;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instafollowers.MainActivity;
import com.example.instafollowers.R;
import com.example.instafollowers.databinding.FragmentFollowedBinding;
import com.example.instafollowers.databinding.FragmentLikedBinding;
import com.example.instafollowers.history.adapters.FollowedUsersAdapter;
import com.example.instafollowers.homepage.UserViewModel;
import com.example.instafollowers.rest.EndpointsInterface;
import com.example.instafollowers.rest.FollowedResponse;
import com.example.instafollowers.rest.LikeResponse;
import com.example.instafollowers.rest.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AndroidEntryPoint
public class FollowedFragment extends Fragment {

    private MainActivity mainActivity;
    private FragmentFollowedBinding binding;
    private UserViewModel viewModel;
    private NavController navController;
    private FollowedFragment fragment;

    public FollowedFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        viewModel = new ViewModelProvider(mainActivity).get(UserViewModel.class);
        navController = mainActivity.getNavController();
        fragment = this;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFollowedBinding.inflate(inflater, container, false);

        getFollowedUsernames();

        FollowedUsersAdapter adapter = new FollowedUsersAdapter(mainActivity);

        viewModel.getFollowedUsernames().observe(
                getViewLifecycleOwner(),
                adapter::setUsernames);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
/*
        viewModel.getFollowedUsernames().observe(
                getViewLifecycleOwner(),
                binding.totalNumber.setText("(total " + viewModel.getFollowedUsernames().getValue().size() + ")"));

*/

        return binding.getRoot();
    }


    private void getFollowedUsernames(){
        EndpointsInterface api = RetrofitClient.getRetrofitInstance().create(EndpointsInterface.class);

        Call<FollowedResponse> request = api.getFollowedUsernames();
        request.enqueue(new Callback<FollowedResponse>() {
            @Override
            public void onResponse(Call<FollowedResponse> call, Response<FollowedResponse> response) {
                Log.d("LIKED_PICTURE", "Response code : " + response.code());

                if(response.code() == 500){
                    navController.navigate(R.id.loginFragment);
                    Toast.makeText(mainActivity, "You must login!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String[] usernames = response.body().getFollowedUsernames();
                    List<String> list = new ArrayList<>(Arrays.asList(usernames));
                    Log.d("LIKED_PICTURE", "duzina liste fotografija : " + usernames.length);

                    viewModel.setFollowedUsernames(list);
                }

            }

            @Override
            public void onFailure(Call<FollowedResponse> call, Throwable error) {
                Log.d("LIKED_FRAGMENT", "Doslo je do grekse!" + error.getMessage());
                Toast.makeText(mainActivity, "Doslo je do grekse!", Toast.LENGTH_LONG).show();
            }
        });
    }
}