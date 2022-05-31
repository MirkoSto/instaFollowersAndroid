package com.example.instafollowers.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.instafollowers.databinding.FragmentLikedBinding;
import com.example.instafollowers.databinding.FragmentLoginBinding;
import com.example.instafollowers.history.adapters.LikedPicturesAdapter;
import com.example.instafollowers.homepage.UserViewModel;
import com.example.instafollowers.rest.EndpointsInterface;
import com.example.instafollowers.rest.LikeResponse;
import com.example.instafollowers.rest.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LikedFragment extends Fragment {

    private MainActivity mainActivity;
    private FragmentLikedBinding binding;
    private UserViewModel viewModel;
    private NavController navController;

    public LikedFragment() {
        // Required empty public constructor
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
        binding = FragmentLikedBinding.inflate(inflater, container, false);

        LikedPicturesAdapter adapter = new LikedPicturesAdapter(viewModel.getHrefs(), mainActivity);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        return binding.getRoot();
    }


    private void getLikedPictures(){
        EndpointsInterface api = RetrofitClient.getRetrofitInstance().create(EndpointsInterface.class);

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
}