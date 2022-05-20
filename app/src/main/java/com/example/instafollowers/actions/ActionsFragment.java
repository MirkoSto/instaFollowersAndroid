package com.example.instafollowers.actions;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.instafollowers.MainActivity;
import com.example.instafollowers.R;
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

            String new_tag = Objects.requireNonNull(binding.newTag.getText()).toString();
            if(new_tag.contains(" ") || new_tag.contains("\n")){
                Toast.makeText(mainActivity, "Tag cannot have blank space!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (allTags.contains(new_tag) || new_tag.equals(""))
                return;
            else
                addNewTag(new_tag);

            CheckBox checkBox = new CheckBox(mainActivity);

            checkBox.setText(new_tag);
            //checkBox.setBackgroundColor(getResources().getColor(R.color.blue_primary));

            checkBox.setOnClickListener(b -> {
                if (checkBox.isChecked()) {
                    selectedTags.add(new_tag); //checkBox.getText().toString()
                    Log.d("TAGS", "Selektovan");
                }
                else {
                    selectedTags.remove(new_tag);
                    Log.d("TAGS", "Nije selektovan");
                }
                Log.d("TAGS", new_tag);
            });

            binding.tagsLayout.addView(checkBox);
        });

        binding.removeTagButton.setOnClickListener(l -> {
            if(selectedTags.size() == 0){
                Toast.makeText(mainActivity, "Please select at least one hashtag you want to follow!", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("TAGS", "Broj tagova: " + allTags.size());
            int num_tags = allTags.size();
            int num_deleted = 0;

            for(int i = 0; i < num_tags; i++){
                CheckBox selectedCB;
                String selectedTag = "null";

                selectedCB = (CheckBox)binding.tagsLayout.getChildAt(i - num_deleted);

                if(selectedCB.isChecked()){
                    num_deleted++;
                    selectedTag = selectedCB.getText().toString();
                    allTags.remove(selectedTag);
                    selectedTags.remove(selectedTag);
                    binding.tagsLayout.removeView(selectedCB);
                }

                Log.d("TAGS", "izbrisan tag " + selectedTag);
            }

            updatePreferencesForTag();
            Log.d("TAGS", "Ukupan broj tagova: " + allTags.size() + " Ukupan broj selektovanih: " + selectedTags.size());
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

    private void updatePreferencesForTag() {

        String new_tags = "";

        for(String tag : allTags){
            if(new_tags.equals(""))
                new_tags = tag;
            else
                new_tags += "," + tag;
        }

        preferences.edit()
                .putString("tags", new_tags)
                .apply();
    }


    private void addNewTag(String new_tag) {
        allTags.add(new_tag);

        String tags_string = "";
        for(String tag : allTags){
            if(tags_string.equals(""))
                tags_string = tag;
            else
                tags_string += "," + tag;
        }
        Log.d("TAGOVI", tags_string);

        preferences.edit()
                .putString("tags", tags_string)
                .apply();
    }


    private void initTagsCheckbox() {

        String tagsInString = preferences.getString("tags", "");
        if (tagsInString.equals("")) return;

        String[] tags = tagsInString.split(",");

        for (String tag : tags) {
            allTags.add(tag);

            CheckBox checkBox = new CheckBox(mainActivity);
            checkBox.setText(tag);
            //checkBox.setColor(getResources().getColor(R.color.blue_primary));

            checkBox.setOnClickListener(l -> {
                if (checkBox.isChecked()){
                    selectedTags.add(tag); //checkBox.getText().toString()
                    Log.d("TAGS", "Selektovan " + tag);
                }

                else {
                    selectedTags.remove(tag);
                    Log.d("TAGS", "Deselektovan " + tag);
                }
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