package com.example.instafollowers.history.adapters;

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
import com.example.instafollowers.databinding.ViewHolderPictureBinding;

import java.util.ArrayList;
import java.util.List;

public class LikedPicturesAdapter extends RecyclerView.Adapter<LikedPicturesAdapter.ViewHolder> {

    private List<String> hrefs;
    private MainActivity mainActivity;


    public LikedPicturesAdapter(List<String> hrefs, MainActivity mainActivity){
        this.hrefs = hrefs;
        this.mainActivity = mainActivity;

        Log.d("LIKED_ADAPTER", "setovana lista, ima " + hrefs.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderPictureBinding viewHolderPictureBinding = ViewHolderPictureBinding.inflate(
                layoutInflater,
                parent,
                false);
        return new ViewHolder(viewHolderPictureBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(hrefs.get(position));
    }

    @Override
    public int getItemCount() {
        return hrefs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ViewHolderPictureBinding binding;

        public ViewHolder(@NonNull ViewHolderPictureBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String href){
            Log.d("LIKED_ADAPTER", "Binding ");
            binding.href.setText(href);

            binding.viewButton.setOnClickListener(click -> {
                Uri uri = Uri.parse(href);
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
