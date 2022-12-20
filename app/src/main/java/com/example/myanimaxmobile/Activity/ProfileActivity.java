package com.example.myanimaxmobile.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import com.example.myanimaxmobile.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView nama, email, genre, role;
    Button editProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
        nama = findViewById(R.id.profilNama);
        email = findViewById(R.id.profilEmail);
        genre = findViewById(R.id.profilGenre);
        role = findViewById(R.id.profilRole);
        editProfile = findViewById(R.id.buttonEdit);

        nama.setText(sharedPreferences.getString("NAME", null));
        email.setText(sharedPreferences.getString("EMAIL", null));
        genre.setText(sharedPreferences.getString("GENRE", null));
        role.setText(sharedPreferences.getString("ROLE", null));

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfile = new Intent(ProfileActivity.this, ChangeProfileActivity.class);
                startActivity(editProfile);
            }
        });
    }
}