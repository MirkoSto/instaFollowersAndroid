package com.example.instafollowers.homepage;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instafollowers.MainActivity;
import com.example.instafollowers.R;
import com.example.instafollowers.databinding.FragmentHomeBinding;
import com.example.instafollowers.rest.EndpointsInterface;
import com.example.instafollowers.rest.LikeResponse;
import com.example.instafollowers.rest.RetrofitClient;
import com.example.instafollowers.rest.HomeStatisticResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private MainActivity mainActivity;
    private FragmentHomeBinding binding;
    private UserViewModel viewModel;
    private NavController navController;


    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();
        viewModel = new ViewModelProvider(mainActivity).get(UserViewModel.class);
        navController = mainActivity.getNavController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        EndpointsInterface api = RetrofitClient.getRetrofitInstance().create(EndpointsInterface.class);
        getStatisticData(api);
        getLikedPictures(api);

        return binding.getRoot();
    }


    private void getLikedPictures(@NonNull EndpointsInterface api){

        Call<LikeResponse> request = api.getLikedPictures();
        request.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                Log.d("LIKED_PICTURE", "Response code : " + response.code());

                if(response.code() == 500){
                    navController.navigate(R.id.loginFragment);
                    Toast.makeText(mainActivity, "You must login!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String[] hrefs = response.body().getLiked_pics();
                    List<String> list = new ArrayList<>(Arrays.asList(hrefs));
                    Log.d("LIKED_PICTURE", "duzina liste fotografija : " + hrefs.length);
                    viewModel.setHrefs(list);
                }

            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable error) {
                Log.d("LIKED_FRAGMENT", "Doslo je do grekse!" + error.getMessage());
                Toast.makeText(mainActivity, "Doslo je do grekse!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getStatisticData(@NonNull EndpointsInterface api){

        Call<HomeStatisticResponse> statisticCall = api.getData();
        statisticCall.enqueue(new Callback<HomeStatisticResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<HomeStatisticResponse> call, Response<HomeStatisticResponse> response) {
                if(response.code() != 200) {
                    Toast.makeText(mainActivity, "Something went wrong! Status code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    String username = response.body().getUsername();
                    String followers_number = response.body().getFollowers_number();
                    String following_number = response.body().getFollowing_number();

                    Log.d("REST", "Followers: " + followers_number + "   Following: " + following_number);

                    binding.username.setText(username);
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

                    viewModel.setFollowers_via_app(response.body().getFollowers_via_app());
                    viewModel.setFollowers_percentage(response.body().getFollowers_percentage());
                    
                    viewModel.setTotal_followed(response.body().getTotal_followed());
                    viewModel.setTotal_unfollowed(response.body().getTotal_unfollowed());
                    viewModel.setTotal_liked(response.body().getTotal_liked());
                    viewModel.setTotal_commented(response.body().getTotal_commented());
                    viewModel.setTotal_stories(response.body().getTotal_stories());



                    binding.todayFollowed.setText("Followed: " + followed + "/" + max_followed);
                    binding.todayUnfollowed.setText("Unfollowed: " + unfollowed + "/" + max_unfollowed);
                    binding.todayLiked.setText("Liked: " + liked + "/" + max_liked);
                    binding.todayCommented.setText("Commented: " + commented + "/" + max_commented);
                    binding.todayStories.setText("Stories: " + stories + "/" + max_stories);
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
            }
        });
    }


}