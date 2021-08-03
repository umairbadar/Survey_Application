package com.example.surveyapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surveyapplication.dao.UsersDAO;
import com.example.surveyapplication.models.Users;

public class SplashActivity extends AppCompatActivity {

    private int logged_in_status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone", "");
        if (!phone.equals("")){
            AppDatabase database = AppDatabase.getAppDatabase(this);
            UsersDAO usersDAO = database.getUsersDAO();
            if (usersDAO.getAllCount() > 0 && usersDAO.checkAlreadyExists(phone) > 0){
                Users user = usersDAO.getUserDetails(phone);
                logged_in_status = user.getLogged_in_status();
                if (database.isOpen())
                    database.close();
            }
        }

        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(() -> {
            Intent intent;
            if (logged_in_status == 1) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }, SPLASH_DISPLAY_LENGTH);
    }
}