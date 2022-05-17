package com.example.instafollowers;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        setNavigationMenu();

        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }


    private void setNavigationMenu(){

        binding.navView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.login_fragment:
                    navController.navigate(R.id.loginFragment);
                    break;

                case R.id.homepage_fragment:
                    navController.navigate(R.id.homeFragment);
                    break;

                case R.id.statistic_fragment:
                    navController.navigate(R.id.statisticFragment);
                    break;

                case R.id.actions_fragment:
                    navController.navigate(R.id.actionsFragment);
                    break;

            }

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
                    super.onBackPressed();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click back again to exit!", Toast.LENGTH_SHORT).show();

                handler.postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            }

        }


    public SharedPreferences getPreferences(){
        return preferences;
    }
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
