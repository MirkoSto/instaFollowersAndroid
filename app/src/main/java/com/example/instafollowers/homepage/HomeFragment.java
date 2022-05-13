package com.example.instafollowers.homepage;

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
import com.example.instafollowers.databinding.FragmentHomeBinding;
import com.example.instafollowers.rest.EndpointsInterface;
import com.example.instafollowers.rest.RetrofitClient;
import com.example.instafollowers.rest.HomeStatisticResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class HomeFragment extends Fragment {

    private MainActivity mainActivity;
    private FragmentHomeBinding binding;
    private HomeStatisticViewModel viewModel;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();
        viewModel = new ViewModelProvider(mainActivity).get(HomeStatisticViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        EndpointsInterface methods = RetrofitClient.getRetrofitInstance().create(EndpointsInterface.class);
        getStatisticData(methods);

        return binding.getRoot();
    }


    private void getStatisticData(@NonNull EndpointsInterface methods){
        //TODO: Cuvati u view modelu, da bi se smanjio broj zahteva! (Mozda nije potrebno!)

        Call<HomeStatisticResponse> statisticCall = methods.getData();
        statisticCall.enqueue(new Callback<HomeStatisticResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<HomeStatisticResponse> call, Response<HomeStatisticResponse> response) {
                if(response.code() != 200) {
                    Toast.makeText(mainActivity, "Something went wrong! Status code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                String followers_number = response.body().getFollowers_number();
                String following_number = response.body().getFollowing_number();

                Log.d("REST", "Followers: " + followers_number + "   Following: " + following_number);

                binding.followers.setText(followers_number + " \nfollowers");
                binding.following.setText(following_number + " \nfollowing");

                int max_followed = response.body().getMax_followed();
                int max_unfollowed = response.body().getMax_unfollowed();
                int max_liked = response.body().getMax_liked();
                int max_commented = response.body().getMax_commented();
                int max_stories = response.body().getMax_stories();

                viewModel.setMax_followed(max_followed);
                viewModel.setMax_unfollowed(max_unfollowed);
                viewModel.setMax_liked(max_liked);
                viewModel.setMax_commented(max_commented);
                viewModel.setMax_stories(max_stories);

                int followed = response.body().getFollowed();
                int unfollowed = response.body().getUnfollowed();
                int liked = response.body().getLiked();
                int commented = response.body().getCommented();
                int stories = response.body().getStories();

                binding.todayFollowed.setText("Followed: " + followed + "/" + max_followed);
                binding.todayUnfollowed.setText("Unfollowed: " + unfollowed + "/" + max_unfollowed);
                binding.todayLiked.setText("Liked: " + liked + "/" + max_liked);
                binding.todayCommented.setText("Commented: " + commented + "/" + max_commented);
                binding.todayStories.setText("Stories: " + stories + "/" + max_stories);
            }

            @Override
            public void onFailure(Call<HomeStatisticResponse> call, Throwable t) {
                Log.d("REST", t.getMessage());
            }
        });
    }


}