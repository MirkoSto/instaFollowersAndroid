package com.example.instafollowers.statistic;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instafollowers.MainActivity;
import com.example.instafollowers.R;
import com.example.instafollowers.databinding.FragmentActionsBinding;
import com.example.instafollowers.databinding.FragmentStatisticBinding;
import com.example.instafollowers.homepage.UserViewModel;
import com.example.instafollowers.rest.EndpointsInterface;
import com.example.instafollowers.rest.HomeStatisticResponse;
import com.example.instafollowers.rest.RetrofitClient;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStatisticBinding.inflate(inflater, container, false);

        getStatisticData();

        return binding.getRoot();
    }


    private void getStatisticData(){

        EndpointsInterface methods = RetrofitClient.getRetrofitInstance().create(EndpointsInterface.class);
        Call<HomeStatisticResponse> statisticCall = methods.getData();
        statisticCall.enqueue(new Callback<HomeStatisticResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<HomeStatisticResponse> call, Response<HomeStatisticResponse> response) {
                if(response.code() != 200) {
                    Toast.makeText(mainActivity, "Something went wrong! Status code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                try{

                    binding.followersViaApp.setText("Followers: " + response.body().getFollowers_via_app());
                    binding.followersPercentage.setText("Followers percentage: " + response.body().getFollowers_percentage());

                    binding.followedByApp.setText("Total followed: " + response.body().getTotal_followed());
                    binding.unfollowedByApp.setText("Total unfollowed: " + response.body().getTotal_unfollowed());
                    binding.likes.setText("Total likes: " + response.body().getTotal_liked());
                    binding.comments.setText("Total comments: " + response.body().getTotal_commented());
                    binding.stories.setText("Total stories: " + response.body().getTotal_stories());

                }
                catch (NullPointerException e){
                    e.printStackTrace();
                    //bacam exception kako bi se ugasila app
                    throw e;
                }

            }

            @Override
            public void onFailure(Call<HomeStatisticResponse> call, Throwable t) {
                Log.d("REST", t.getMessage());
                Toast.makeText(mainActivity, "ERROR : " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}