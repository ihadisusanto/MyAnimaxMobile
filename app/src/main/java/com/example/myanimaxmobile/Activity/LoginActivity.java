package com.example.myanimaxmobile.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myanimaxmobile.R;
import com.example.myanimaxmobile.response.userMe;
import com.example.myanimaxmobile.request.restAPIInterface;
import com.example.myanimaxmobile.request.Service;
import com.example.myanimaxmobile.response.userLogin;
import com.google.android.material.textfield.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout user, pass;
    private Button loginButton;
    private String token;
    private ProgressDialog progressDialog;

    private static final String TAG = "LoginActivity";
    private SharedPreferences.Editor session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = getSharedPreferences("USER_LOGIN",MODE_PRIVATE).edit();
        user = findViewById(R.id.usernameField);
        pass = findViewById(R.id.passwordField);
        View linkBuatAkun = findViewById(R.id.linkBuatAkun);
        linkBuatAkun.setOnClickListener(v -> {
            // opening a new activity via a intent.
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    runOnUiThread(() -> {
                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage("Now Loading...");
                        progressDialog.show();
                    });
                    try{
                        login();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    runOnUiThread(() -> {
                        progressDialog.dismiss();
                    });
                });
            }
        });
    }

    public void login(){
        String username = user.getEditText().getText().toString();
        String password = pass.getEditText().getText().toString();
        if (username.isEmpty()) {
            user.setError("Username tidak boleh kosong");
            user.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Password tidak boleh kosong");
            pass.requestFocus();
            return;
        }
        restAPIInterface apiInterface = Service.getRetrofitInstance().create(restAPIInterface.class);
        Call<userLogin> call = apiInterface.getLoginInformation(username, password);
        call.enqueue(new Callback<userLogin>() {
            @Override
            public void onResponse(Call<userLogin> call, Response<userLogin> response) {
                if(response.code()==200) {
                    Log.e(TAG, "onResponse: " + response.code());
                    Log.e(TAG, "onResponse:  username = " + response.body().getUsername());
                    Log.e(TAG, "onResponse:  token = " + response.body().getToken());
                    Log.e(TAG, "onResponse:  role = " + response.body().getRole());
                    Log.e(TAG, "onResponse:  status = " + response.body().getStatus());
                    token  = response.body().getToken();
                    session.putString("TOKEN", response.body().getToken());
                    session.putString("USERNAME", response.body().getUsername());
                    session.putString("NAME", response.body().getName());
                    session.apply();
                    loadProfile();
                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    user.getEditText().setText("");
                    pass.getEditText().setText("");
                    user.requestFocus();
                    Toast.makeText(LoginActivity.this, "Username atau Password Salah",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<userLogin> call, Throwable t) {
                Log.e(TAG, "Hayoo gagal login: ");
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void loadProfile(){
        restAPIInterface apiInterface = Service.getRetrofitInstance().create(restAPIInterface.class);
        Call<userMe> call = apiInterface.getMeInformation("Bearer "+token);
        call.enqueue(new Callback<userMe>() {
            @Override
            public void onResponse(Call<userMe> call, Response<userMe> response) {
                if(response.code()==200) {
                    Log.e(TAG, "onResponse: " + response.code());
                    Log.e(TAG, "onResponse:  name = " + response.body().getName());
                    Log.e(TAG, "onResponse:  email = " + response.body().getEmail());
                    Log.e(TAG, "onResponse:  role = " + response.body().getRole());
                    Log.e(TAG, "onResponse:  genre = " + response.body().getGenre());
                    session.putString("EMAIL", response.body().getEmail());
                    session.putString("ROLE", response.body().getRole());
                    session.putString("GENRE", response.body().getGenre());
                    session.apply();
                }
                else{
                    Log.e(TAG, "onResponse: " + response.code());
                    Log.e(TAG, "onResponse: Token gagal di fetch");
                }
            }
            @Override
            public void onFailure(Call<userMe> call, Throwable t) {
                Log.e(TAG, "Hayoo gagal fetch My Profile: ");
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}