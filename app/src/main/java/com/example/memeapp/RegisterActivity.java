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

public class RegisterActivity extends AppCompatActivity {

    ImageView registerScreenBackButton;

    TextView registerScreenBackToLoginButton;

    EditText registerScreenLoginInput, registerScreenPasswordInput, registerScreenEmailInput;

    ProgressBar registerScreenProgress;

    Button registerScreenRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerScreenBackButton = findViewById(R.id.registerScreenBackButton);

        registerScreenBackToLoginButton = findViewById(R.id.registerScreenBackToLoginButton);

        registerScreenBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        });

        registerScreenBackToLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        //Register method
        registerScreenLoginInput = findViewById(R.id.registerScreenLoginInput);
        registerScreenPasswordInput = findViewById(R.id.registerScreenPasswordInput);
        registerScreenEmailInput = findViewById(R.id.registerScreenEmailInput);

        registerScreenProgress = findViewById(R.id.registerScreenProgress);

        registerScreenRegisterButton = findViewById(R.id.registerScreenRegisterButton);

        registerScreenRegisterButton.setOnClickListener(v -> {
            final String login, email, password;
            login = String.valueOf(registerScreenLoginInput.getText());
            password = String.valueOf(registerScreenPasswordInput.getText());
            email = String.valueOf(registerScreenEmailInput.getText());

            if(!login.equals("") && !password.equals("") && !email.equals("")) {
                registerScreenProgress.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[3];
                        field[0] = "user_login";
                        field[1] = "user_password";
                        field[2] = "user_email";
                        String[] data = new String[3];
                        data[0] = login;
                        data[1] = password;
                        data[2] = email;
                        PutData putData = new PutData("http://146.59.17.245/backend/signup.php", "POST", field, data);
                        if(putData.startPut()) {
                            if(putData.onComplete()) {
                                registerScreenProgress.setVisibility(View.GONE);
                                String result = putData.getResult();
                                if(result.equals("Sign up success")) {
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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