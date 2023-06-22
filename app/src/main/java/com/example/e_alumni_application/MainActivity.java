package com.example.e_alumni_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button LoginSplash, RegisterSplash, AdminSplash;
    ProgressBar progressBar;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
     /*   progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        if (auth.getCurrentUser() !=null) {
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(this, "please wait you are already logged in", Toast.LENGTH_SHORT).show();
        }*/

        LoginSplash = findViewById(R.id.splash_login);
        RegisterSplash = findViewById(R.id.splash_register);
        AdminSplash = findViewById(R.id.admin_login);

        LoginSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        RegisterSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));

            }
        });

        AdminSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Admin.class));
            }

        });
    }
}