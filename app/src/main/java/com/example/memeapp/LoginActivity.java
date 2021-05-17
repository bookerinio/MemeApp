package com.example.memeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {

    ImageView loginScreenBackButton;

    TextView loginScreenBackToRegisterButton;

    EditText loginScreenLoginInput, loginScreenPasswordInput;

    ProgressBar loginScreenProgress;

    Button loginScreenLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginScreenBackButton = findViewById(R.id.loginScreenBackButton);

        loginScreenBackToRegisterButton = findViewById(R.id.loginScreenBackToRegisterButton);

        loginScreenBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        });

        loginScreenBackToRegisterButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        //Login method
        loginScreenLoginInput = findViewById(R.id.loginScreenLoginInput);
        loginScreenPasswordInput = findViewById(R.id.loginScreenPasswordInput);

        loginScreenProgress = findViewById(R.id.loginScreenProgress);

        loginScreenLoginButton = findViewById(R.id.loginScreenLoginButton);

        loginScreenLoginButton.setOnClickListener(v -> {
            final String login, password;
            login = String.valueOf(loginScreenLoginInput.getText());
            password = String.valueOf(loginScreenPasswordInput.getText());

            if(!login.equals("") && !password.equals("")) {
                loginScreenProgress.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "user_login";
                        field[1] = "user_password";
                        String[] data = new String[2];
                        data[0] = login;
                        data[1] = password;
                        PutData putData = new PutData("http://146.59.17.245/backend/login.php", "POST", field, data);
                        if(putData.startPut()) {
                            if(putData.onComplete()) {
                                loginScreenProgress.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if(result.equals("Login success")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
            }
        });
    }
}