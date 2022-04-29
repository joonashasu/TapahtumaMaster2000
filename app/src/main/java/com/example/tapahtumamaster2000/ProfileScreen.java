package com.example.tapahtumamaster2000;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.Map;

public class ProfileScreen extends AppCompatActivity {

    private TextView tName;
    private TextView tUsername;
    private TextView tLocation;
    private EditText etPassword1;
    private EditText etPassword2;
    private EditText etLocationChanger;
    private Button bUploadImage;
    private Button bChangePassword;
    private Button bChangeLocation;

    private DrawerLayout mDrawer;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView nvDrawer;

    public Credentials credentials;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        tName = (TextView) findViewById(R.id.tName);
        tUsername = (TextView) findViewById(R.id.tUsername);
        tLocation = (TextView) findViewById(R.id.tLocation);
        etPassword1 = (EditText) findViewById(R.id.etPassword1);
        etPassword2 = (EditText) findViewById(R.id.etPassword2);
        etLocationChanger = (EditText) findViewById(R.id.etLocationChanger);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        bChangePassword = (Button) findViewById(R.id.bChangePassword);
        bChangeLocation = (Button) findViewById(R.id.bChangeLocation);
        String name, username, location;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.home_navigation_drawer_open, R.string.home_navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDataBase", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null){

            Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();
            credentials.credentialLoader(sharedPreferencesMap);

            username = sharedPreferences.getString("LoginUsername", "");
            name = sharedPreferences.getString(credentials.nameLoader(username), "");
            location = sharedPreferences.getString(credentials.locationLoader(username), "");

            tName.setText(name);
            tUsername.setText(username);
            tLocation.setText(location);
        }

        bChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etPassword1 = (EditText) findViewById(R.id.etPassword1);
                etPassword2 = (EditText) findViewById(R.id.etPassword2);

                if(sharedPreferences != null) {
                    Map<String, ?> sharedPreferencesMap = sharedPreferences.getAll();
                    credentials.credentialLoader(sharedPreferencesMap);
                }

                String username = sharedPreferences.getString("LoginUsername", "");
                String oldPassword = sharedPreferences.getString("LoginPassword", "");
                String newPassword1 = etPassword1.getText().toString();
                String newPassword2 = etPassword2.getText().toString();

                if(newPassword1.equals(newPassword2) && !newPassword1.equals(oldPassword)){
                    Toast.makeText(ProfileScreen.this, "Passwords changed successfully", Toast.LENGTH_SHORT).show();
                    sharedPreferencesEditor.putString(username, newPassword1);
                    sharedPreferencesEditor.apply();
                }else if (newPassword1.equals(newPassword2) && newPassword1.equals(oldPassword)) {
                    Toast.makeText(ProfileScreen.this, "Passwords already in use", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProfileScreen.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etLocationChanger = (EditText) findViewById(R.id.etLocationChanger);

                String newLocation = etLocationChanger.getText().toString();

                Toast.makeText(ProfileScreen.this, "Location changed successfully", Toast.LENGTH_SHORT).show();
                sharedPreferencesEditor.putString("LoginLocation", newLocation);
                sharedPreferencesEditor.apply();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Intent intent;
        switch(menuItem.getItemId()) {
            case R.id.nav_browsing_screen:
                intent = new Intent(ProfileScreen.this, BrowseScreen.class);
                break;
            case R.id.nav_user_profile:
                intent = new Intent(ProfileScreen.this, ProfileScreen.class);
                break;
            case R.id.nav_log_out:
                intent = new Intent(ProfileScreen.this, StartScreen.class);
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                break;
            default:
                intent = new Intent(ProfileScreen.this, ProfileScreen.class);
        }


        startActivity(intent);
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    public void lookSaved(View v){
        Intent intent1 = new Intent(ProfileScreen.this, SavedEventsScreen.class);
        startActivity(intent1);
    }
}