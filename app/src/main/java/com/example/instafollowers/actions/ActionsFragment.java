package com.example.instafollowers.actions;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.instafollowers.MainActivity;
import com.example.instafollowers.databinding.FragmentActionsBinding;
import com.example.instafollowers.homepage.UserViewModel;
import com.example.instafollowers.rest.EndpointsInterface;
import com.example.instafollowers.rest.RetrofitClient;
import com.example.instafollowers.rest.ActionResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ActionsFragment extends Fragment {

    private FragmentActionsBinding binding;
    private MainActivity mainActivity;
    private UserViewModel viewModel;
    private SharedPreferences preferences;
    private ArrayList<String> selectedTags;
    private ArrayList<String> allTags;

    public ActionsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) requireActivity();
        viewModel = new ViewModelProvider(mainActivity).get(UserViewModel.class);
        preferences = mainActivity.getPreferences();
        selectedTags = new ArrayList<>();
        allTags = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentActionsBinding.inflate(inflater, container, false);

        initTagsCheckbox();

        binding.addTagButton.setOnClickListener(l -> {

            String tag = Objects.requireNonNull(binding.newTag.getText()).toString();
            if (allTags.contains(tag))
                return;
            else
                allTags.add(tag);


            CheckBox checkBox = new CheckBox(mainActivity);

            checkBox.setText(tag);

            checkBox.setOnClickListener(b -> {
                if (checkBox.isChecked()) {
                    selectedTags.add(tag); //checkBox.getText().toString()
                    Log.d("TAGS", "Selektovan");
                }
                else {
                    selectedTags.remove(tag);
                    Log.d("TAGS", "Nije selektovan");
                }
                Log.d("TAGS", tag);
            });

            binding.tagsLayout.addView(checkBox);
        });

        EndpointsInterface api = RetrofitClient.getRetrofitInstance().create(EndpointsInterface.class);

        binding.followButton.setOnClickListener(click -> {
            follow(api);
        });

        binding.unfollowButton.setOnClickListener(click -> {
            unfollow(api);
        });

        binding.likeButton.setOnClickListener(click -> {
            like(api);
        });

        return binding.getRoot();
    }


    private void initTagsCheckbox() {

        String tagsInString = preferences.getString("tags", "");
        if (tagsInString.equals("")) return;

        String[] tags = tagsInString.split(",");

        for (String tag : tags) {
            allTags.add(tag);

            CheckBox checkBox = new CheckBox(mainActivity);
            checkBox.setText(tag);
            checkBox.setOnClickListener(l -> {
                if (l.isSelected())
                    selectedTags.add(tag); //checkBox.getText().toString()
                else
                    selectedTags.remove(tag);
                Log.d("TAGS", tag);
            });

            binding.tagsLayout.addView(checkBox);
        }

    }

    private void follow(@NonNull EndpointsInterface api) {
        if (selectedTags.size() == 0) {
            Toast.makeText(mainActivity, "Please select at least one hashtag you want to follow!", Toast.LENGTH_SHORT).show();
            return;
        }

        String tags = "";
        for (String tag : selectedTags) {
            if (!tags.equals("")) tags = tags + "," + tag;
            else tags = tag;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("tags", tags);
        Call<ActionResponse> response = api.follow(params);
        response.enqueue(new Callback<ActionResponse>() {
            @Override
            public void onResponse(Call<ActionResponse> call, Response<ActionResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(mainActivity, "Something went wrong! Status code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("FOLLOWING", response.body().getResponse());
                Toast.makeText(mainActivity, "Started following!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ActionResponse> call, Throwable t) {
                Toast.makeText(mainActivity, "GRESKA " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void unfollow(@NonNull EndpointsInterface api) {
        Call<ActionResponse> response = api.unfollow();
        response.enqueue(new Callback<ActionResponse>() {
            @Override
            public void onResponse(Call<ActionResponse> call, Response<ActionResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(mainActivity, "Something went wrong! Status code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(mainActivity, "Started ufollowing!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ActionResponse> call, Throwable t) {
                Toast.makeText(mainActivity, "Unsuccessful request, try again later! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void like(EndpointsInterface api) {
        if (selectedTags.size() == 0) {
            Toast.makeText(mainActivity, "Please select at least one hashtag you want to follow!", Toast.LENGTH_SHORT).show();
            return;
        }

        String tags = "";
        for (String tag : selectedTags) {
            if (!tags.equals("")) tags = tags + "," + tag;
            else tags = tag;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("tags", tags);
        Call<ActionResponse> response = api.like(params);
        response.enqueue(new Callback<ActionResponse>() {
            @Override
            public void onResponse(Call<ActionResponse> call, Response<ActionResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(mainActivity, "Something went wrong! Status code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(mainActivity, "Started ufollowing!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ActionResponse> call, Throwable t) {
                Toast.makeText(mainActivity, "Unsuccessful request, try again later! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}