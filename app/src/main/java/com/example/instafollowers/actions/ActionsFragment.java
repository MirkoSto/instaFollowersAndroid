package com.example.instafollowers.actions;

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
import com.example.instafollowers.databinding.FragmentActionsBinding;
import com.example.instafollowers.homepage.HomeStatisticViewModel;
import com.example.instafollowers.rest.EndpointsInterface;
import com.example.instafollowers.rest.RetrofitClient;
import com.example.instafollowers.rest.StartFollowingResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActionsFragment extends Fragment {

    private FragmentActionsBinding binding;
    private MainActivity mainActivity;
    private OkHttpClient httpClient;
    private HomeStatisticViewModel viewModel;

    public ActionsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        httpClient = new OkHttpClient();
        viewModel = new ViewModelProvider(mainActivity).get(HomeStatisticViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentActionsBinding.inflate(inflater, container, false);

        EndpointsInterface methods = RetrofitClient.getRetrofitInstance().create(EndpointsInterface.class);

        Log.d("Podaci", "Max followed: " + viewModel.getMax_followed());

        binding.followButton.setOnClickListener(click -> {

            if(binding.tagFollow.getText().toString().equals("")){
                Toast.makeText(mainActivity, "Please enter hashtag you want to follow!", Toast.LENGTH_SHORT).show();
                return;
            }

            startFollowing(methods);
        });

        new Thread(() -> {
            try {
                login("dd4085222", "newpassword.1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        return binding.getRoot();
    }



    public void login(String username, String password) throws IOException {

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("https://www.instagram.com/accounts/login/")
                //    .header("Referer", "https://www.instagram.com/accounts/login/")

                .post(formBody)
                .build();

        okhttp3.Response response = this.httpClient.newCall(request).execute();

        Log.d("HTML_RESPONSE", response.body().string());

    }

    private void startFollowing(@NonNull EndpointsInterface methods){
        Map<String, String> params = new HashMap<>();
        params.put("tag", binding.tagFollow.getText().toString());
        params.put("follow_number", viewModel.getMax_followed()+"");
        Call<StartFollowingResponse> response = methods.startFollowing(params);
        response.enqueue(new Callback<StartFollowingResponse>() {
            @Override
            public void onResponse(Call<StartFollowingResponse> call, Response<StartFollowingResponse> response) {
                if(response.code() != 200) {
                    Toast.makeText(mainActivity, "Something went wrong! Status code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("FOLLOWING", response.body().getResponse());
                Toast.makeText(mainActivity, "Started following!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<StartFollowingResponse> call, Throwable t) {
                Toast.makeText(mainActivity, "GRESKA " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}