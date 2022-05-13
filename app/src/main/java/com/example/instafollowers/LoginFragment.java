package com.example.instafollowers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instafollowers.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private MainActivity mainActivity;
    private NavController navController;
    private FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);


        binding.loginButton.setOnClickListener(v -> {
           /* if(!binding.usernameLogin.equals("") && !binding.passwordLogin.equals("")){
                navController.navigate(R.id.homeFragment);
            }
            else Toast.makeText(mainActivity, "Unesite podatke", Toast.LENGTH_SHORT).show();
*/
            navController.navigate(R.id.homeFragment);
        });

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}