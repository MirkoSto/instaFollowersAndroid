package com.example.instafollowers.history.adapters;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instafollowers.MainActivity;
import com.example.instafollowers.databinding.ViewHolderFollowedBinding;
import com.example.instafollowers.databinding.ViewHolderPictureBinding;

import java.util.List;

public class FollowedUsersAdapter extends RecyclerView.Adapter<FollowedUsersAdapter.ViewHolder> {

    private List<String> usernames;
    private MainActivity mainActivity;

    public FollowedUsersAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }


    public void setUsernames(List<String> usernames){
        this.usernames = usernames;
        notifyDataSetChanged();

        Log.d("FOLLOWED_ADAPTER", "setovana lista zapracenih, ima " + usernames.size());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderFollowedBinding viewHolderFollowedBinding = ViewHolderFollowedBinding.inflate(
                layoutInflater,
                parent,
                false);
        return new ViewHolder(viewHolderFollowedBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position, usernames.get(position));
    }

    @Override
    public int getItemCount() {
        return usernames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ViewHolderFollowedBinding binding;

        public ViewHolder(@NonNull ViewHolderFollowedBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(int position, String username){
            binding.usernameNumber.setText(position + 1 + ".");
            binding.username.setText(username);

            binding.viewButton.setOnClickListener(click -> {
                Uri uri = Uri.parse("https://www.instagram.com/" + username);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    mainActivity.startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    mainActivity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            });
        }
    }
}
