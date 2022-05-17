package com.example.instafollowers.login;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AndroidEntryPoint
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


        binding.showPassBtn.setOnClickListener( v -> {
            if(binding.passwordLogin.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){

                //prikazi sifru
                binding.passwordLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.showPassBtn.setImageAlpha(128);
            }
            else {
                //sakrij sifru
                binding.passwordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.showPassBtn.setImageAlpha(255);
            }
        });


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
                        .putString("username", username)
                        .putString("password", password)
                        .apply();
            }

            @Override
            public void onFailure(Call<LoginRequest> call, Throwable error) {
                progressDialog.dismiss();
                Toast.makeText(mainActivity, "Doslo je do grekse u logovanju 1!", Toast.LENGTH_LONG).show();
                Log.d("LOGOVANJE", "Doslo je do grekse u logovanju 1!" + error.getMessage());

                mainActivity.getPreferences().edit()
                        .remove("username")
                        .remove("password")
                        .apply();
            }
        });


        Log.d("LOGOVANJE", "Cekanje 10 sekundi za proveru statusa!");
        isLogged(api, progressDialog, 20 * 1000);



    }

    //Posto se jedna fja koristi dva puta, mora se proveriti da li je vec ulogovan
    private void isLogged(EndpointsInterface api, ProgressDialog progressDialog, int delayTime){

        Handler handler = new Handler();
        Log.d("LOGOVANJE", "Pocetak provere logovanja");

        handler.postDelayed(() -> {
            Log.d("LOGOVANJE", "Prosao posle 20sec ?");

            Call<LoginResponse> response = api.isLogged();
            response.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    String message = response.body().getMessage();
                    Log.d("LOGOVANJE", "response message = " + message);
                    if (message.equals("logged")) {
                        Log.d("LOGOVANJE", "ulogovan!");
                        mainActivity.getPreferences().edit()
                                .putBoolean("logged", true)
                                .apply();

                        progressDialog.dismiss();
                        navController.navigate(R.id.homeFragment);

                    }
                    else{
                        Toast.makeText(mainActivity, "Neuspelo logovanje!", Toast.LENGTH_LONG).show();
                        Log.d("LOGOVANJE", "Neuspelo logovanje!");
                        progressDialog.dismiss();
                        mainActivity.getPreferences().edit()
                                .remove("username")
                                .remove("password")
                                .putBoolean("login_failed", true)
                                .putBoolean("logged", false)
                                .apply();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable error) {
                    progressDialog.dismiss();
                    Toast.makeText(mainActivity, "Doslo je do grekse u logovanju 2!", Toast.LENGTH_LONG).show();
                    Log.d("LOGOVANJE", error.getMessage());
                    mainActivity.getPreferences().edit()
                            .remove("username")
                            .remove("password")
                            .putBoolean("login_failed", true)
                            .putBoolean("logged", false)
                            .apply();
                }
            });
        }, delayTime);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}