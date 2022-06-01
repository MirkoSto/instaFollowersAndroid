package com.example.instafollowers;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.instafollowers.databinding.ActivityMainBinding;


import java.net.MalformedURLException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private ActivityMainBinding binding;
    private NavController navController;
    private DrawerLayout drawer;
    private boolean doubleBackToExitPressedOnce = false;

    private int currentFragmentId;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferences = getPreferences(Context.MODE_PRIVATE);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_container);

        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        drawer = binding.drawerLayout;

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);



        if(preferences.getBoolean("logged", false)) {
            currentFragmentId = R.id.homeFragment;
            navController.navigate(R.id.homeFragment);
        }
        else {
            currentFragmentId = R.id.loginFragment;
            navController.navigate(R.id.loginFragment);
        }

        setNavigationMenu();

        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }


    private void setNavigationMenu(){
        NavOptions options = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .build();

        binding.navView.setNavigationItemSelectedListener(item -> {
            boolean cleared = false;
            switch (item.getItemId()) {

                case R.id.login_fragment:
                    navController.navigate(R.id.loginFragment, null, options);
                    currentFragmentId = R.id.login_fragment;
                    break;

                case R.id.homepage_fragment:
                    navController.navigate(R.id.homeFragment, null, options);

                    currentFragmentId = R.id.homepage_fragment;
                    break;

                case R.id.statistic_fragment:
                    navController.navigate(R.id.statisticFragment, null, options);
                    currentFragmentId = R.id.statistic_fragment;
                    break;

                case R.id.actions_fragment:
                    navController.navigate(R.id.actionsFragment, null, options);
                    currentFragmentId = R.id.actions_fragment;
                    break;

                case R.id.liked_fragment:
                    navController.navigate(R.id.likedFragment, null, options);
                    currentFragmentId = R.id.likedFragment;
                    break;

                case R.id.followed_fragment:
                    navController.navigate(R.id.followedFragment, null, options);
                    currentFragmentId = R.id.followedFragment;
                    break;

            }

            Log.d("FRAGMENTI", "cleared? " + cleared);

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

    }


    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else{
                if (doubleBackToExitPressedOnce) {
                    this.finish();
                }

                this.doubleBackToExitPressedOnce = true;


                handler.postDelayed(() ->doubleBackToExitPressedOnce = false, 2000);

                //TODO: popraviti prikaz toast poruke
                if(!doubleBackToExitPressedOnce)
                    Toast.makeText(this, "Please click back again to exit!", Toast.LENGTH_SHORT).show();
        }

        }


    public SharedPreferences getPreferences(){
        return preferences;
    }

    public NavController getNavController() { return navController; }
}


/*
  System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);

        Thread thread = new Thread(() -> {
            driver = new ChromeDriver(options);
            driver.get("https://google.com");
            Log.d("PAGE_SOURCE", driver.getPageSource());
        });
        thread.start();

 */
