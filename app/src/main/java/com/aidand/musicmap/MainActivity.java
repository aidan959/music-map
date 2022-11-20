package com.aidand.musicmap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.aidand.musicmap.database.AppDatabase;
import com.aidand.musicmap.services.ListensService;
import com.aidand.musicmap.ui.settings.AppSettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.aidand.musicmap.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public boolean locationEnabled = false;
    public boolean phoneStateEnabled = false;
    public AppDatabase globalDb;
    public NavController navController;
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO MAKE THIS ASYNC AND MAKE OTHER THREADS WAIT FOR THIS DATA
        globalDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "listen-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        startService(new Intent(this, ListensService.class));
        checkLocationPermission();

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map, R.id.navigation_listens, R.id.navigation_nowplaying)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        this.navController =  navController;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.settings_button:
                // start the settings activity
                Intent settingsIntent = new Intent(getApplicationContext(), AppSettingsActivity.class);
                startActivity(settingsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_PERMISSIONS_REQUEST_PHONE_STATE = 100;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        locationEnabled = true;
                    }

                } else {
                    locationEnabled = false;
                }
            }
            case MY_PERMISSIONS_REQUEST_PHONE_STATE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_GRANTED) {
                        phoneStateEnabled = true;
                    }
                } else {
                    phoneStateEnabled = false;
                }
            }
        }
    }
}