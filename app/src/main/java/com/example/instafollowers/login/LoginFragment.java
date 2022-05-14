package com.example.instafollowers.login;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instafollowers.MainActivity;
import com.example.instafollowers.R;
import com.example.instafollowers.databinding.FragmentLoginBinding;
import com.example.instafollowers.rest.EndpointsInterface;
import com.example.instafollowers.rest.LoginRequest;
import com.example.instafollowers.rest.LoginResponse;
import com.example.instafollowers.rest.RetrofitClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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

        final ProgressDialog progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please wait"); // set message
        progressDialog.show(); // show progress dialog

        EndpointsInterface api = RetrofitClient.getRetrofitInstance().create(EndpointsInterface.class);

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        Call<LoginRequest> request = api.login(params);
        request.enqueue(new Callback<LoginRequest>() {
            @Override
            public void onResponse(Call<LoginRequest> call, Response<LoginRequest> response) {

                mainActivity.getPreferences().edit()
                        .putBoolean("logging", true)
                        .putString("username", username)
                        .putString("password", password)
                        .apply();
            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable error) {
                progressDialog.dismiss();
                Toast.makeText(mainActivity, "Doslo je do grekse u logovanju 1!", Toast.LENGTH_LONG).show();
                Log.d("LOGOVANJE", error.getMessage());

                mainActivity.getPreferences().edit()
                        .putBoolean("logged", false)
                        .apply();
            }
        });

        AtomicBoolean logging = new AtomicBoolean(mainActivity.getPreferences().getBoolean("logging", false));

        Handler handler = new Handler();
        Log.d("LOGOVANJE", "Pocetak provere logovanja");


       // while(logging.get()) {
            handler.postDelayed(() -> {
                Log.d("LOGOVANJE", "Prosao posle 5sec ?");

                Call<LoginResponse> response = api.isLogged();
                response.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        String message = response.body().getMessage();

                        if (message.equals("logged")) {
                            Log.d("LOGOVANEJ", "ulogovan");
                            mainActivity.getPreferences().edit()
                                    .remove("logging")
                                    .putBoolean("logged", true)
                                    .apply();


                            progressDialog.dismiss();
                            navController.navigate(R.id.homeFragment);

                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable error) {
                        progressDialog.dismiss();
                        Toast.makeText(mainActivity, "Doslo je do grekse u logovanju 2!", Toast.LENGTH_LONG).show();
                        Log.d("LOGOVANJE", error.getMessage());
                        mainActivity.getPreferences().edit()
                                .remove("logging")
                                .remove("username")
                                .remove("password")
                                .putBoolean("logged", false)
                                .apply();
                    }
                });

                logging.set(mainActivity.getPreferences().getBoolean("logging", false));

            }, 8000);
   //     }

        //ako nije doslo ni do kakvog problema u logovanju, ulogovao se, zatvori progressDialog
       // Log.d("LOGOVANJE", "provera za gasenje dialoga");

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}