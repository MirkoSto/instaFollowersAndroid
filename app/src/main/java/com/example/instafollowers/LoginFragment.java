package com.example.instafollowers;

import android.app.ProgressDialog;
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
import com.example.instafollowers.rest.EndpointsInterface;
import com.example.instafollowers.rest.LoginResponse;
import com.example.instafollowers.rest.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);



        binding.loginButton.setOnClickListener(v -> {
            String username = binding.usernameLogin.getText().toString().trim();
            String password = binding.passwordLogin.getText().toString().trim();

            if(username.length() == 0 || password.length() == 0)
                Toast.makeText(mainActivity, "Unesite podatke", Toast.LENGTH_SHORT).show();

            else{
                login(username, password);
            }


        });

        return binding.getRoot();
    }

    private void login(String username, String password) {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        EndpointsInterface api = RetrofitClient.getRetrofitInstance().create(EndpointsInterface.class);

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        Call<LoginResponse> request = api.login(params);
        request.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                mainActivity.getPreferences().edit()
                        .putBoolean("loggedIn", true)
                        .putString("username", username)
                        .putString("password", password)
                        .apply();

                navController.navigate(R.id.homeFragment);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable error) {
                progressDialog.dismiss();
                Toast.makeText(mainActivity, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}