package com.example.myanimaxmobile.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myanimaxmobile.R;
import com.example.myanimaxmobile.response.userUpdate;
import com.example.myanimaxmobile.request.Service;
import com.example.myanimaxmobile.request.restAPIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileActivity extends AppCompatActivity {
    private EditText nama,email,genre;
    private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_LOGIN",MODE_PRIVATE);
        nama = findViewById(R.id.oldPass);
        email = findViewById(R.id.newPass);
        genre = findViewById(R.id.confirmPass);
        nama.setText(sharedPreferences.getString("NAME",null));
        email.setText(sharedPreferences.getString("EMAIL",null));
        genre.setText(sharedPreferences.getString("GENRE",null));

        //update profile
        update = findViewById(R.id.updatePassButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

    }

    public void updateProfile(){
        String name = nama.getText().toString();
        String emailUser = email.getText().toString();
        String genreUser = genre.getText().toString();
        String token = "Bearer "+getSharedPreferences("USER_LOGIN",MODE_PRIVATE).getString("TOKEN",null);
        String username = getSharedPreferences("USER_LOGIN",MODE_PRIVATE).getString("USERNAME",null);
        if(nama.getText().toString().isEmpty()) {
            nama.setError("Nama tidak boleh kosong");
            nama.requestFocus();
            return;
        }
        if(email.getText().toString().isEmpty()) {
            email.setError("Email tidak boleh kosong");
            email.requestFocus();
            return;
        }
        if(genre.getText().toString().isEmpty()) {
            genre.setError("Genre tidak boleh kosong");
            genre.requestFocus();
            return;
        }
        dummy body = new dummy(name,emailUser,genreUser);
        restAPIInterface apiInterface = Service.getRetrofitInstance().create(restAPIInterface.class);
        Call<userUpdate> call = apiInterface.updateUser(token,username,body);
        call.enqueue(new Callback<userUpdate>(){
            @Override
            public void onResponse(Call<userUpdate> call, Response<userUpdate> response){
                if(response.code()==200){
                    SharedPreferences.Editor session = getSharedPreferences("USER_LOGIN",MODE_PRIVATE).edit();
                    session.putString("NAME",name);
                    session.putString("EMAIL",emailUser);
                    session.putString("GENRE",genreUser);
                    session.apply();
                    Intent intent = new Intent(ChangeProfileActivity.this,ProfileActivity.class);
                    startActivity(intent);
                    Toast.makeText(ChangeProfileActivity.this,"Update Profile Berhasil",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ChangeProfileActivity.this,"Update Profile Gagal",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<userUpdate> call, Throwable t){
                Toast.makeText(ChangeProfileActivity.this,"Update Profile Gagal",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class dummy {
        private String name;
        private String email;
        private String genre;

        public dummy(String name, String email, String genre) {
            this.name = name;
            this.email = email;
            this.genre = genre;
        }
    }
}