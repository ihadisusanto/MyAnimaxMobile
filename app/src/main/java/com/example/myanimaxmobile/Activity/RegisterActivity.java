package com.example.myanimaxmobile.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myanimaxmobile.R;
import com.example.myanimaxmobile.response.userRegister;
import com.example.myanimaxmobile.request.Service;
import com.example.myanimaxmobile.request.restAPIInterface;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText namaField,emailField,usernameField,genreField,passwordField,confirmPasswordField;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        View loginLink = findViewById(R.id.loginLink);
        loginLink.setOnClickListener(v -> {
            // opening a new activity via a intent.
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        });

        usernameField = findViewById(R.id.usernameRegisterField);
        namaField = findViewById(R.id.fullNameRegisterField);
        emailField = findViewById(R.id.emailRegisterField);
        genreField = findViewById(R.id.genreRegisterField);
        passwordField = findViewById(R.id.passwordRegisterField);
        confirmPasswordField = findViewById(R.id.confirmPasswordRegisterField);

        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register(){
        String username = usernameField.getText().toString();
        String nama = namaField.getText().toString();
        String email = emailField.getText().toString();
        String genre = genreField.getText().toString();
        String password = passwordField.getText().toString();
        String confpassword = confirmPasswordField.getText().toString();

        if (username.isEmpty()) {
            usernameField.setError("Username tidak boleh kosong");
            usernameField.requestFocus();
            return;
        }
        if (nama.isEmpty()) {
            namaField.setError("Nama tidak boleh kosong");
            namaField.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailField.setError("Email tidak boleh kosong");
            emailField.requestFocus();
            return;
        }
        if (genre.isEmpty()) {
            genreField.setError("Genre tidak boleh kosong");
            genreField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordField.setError("Password tidak boleh kosong");
            passwordField.requestFocus();
            return;
        }
        if (confpassword.isEmpty()) {
            confirmPasswordField.setError("Konfirmasi password tidak boleh kosong");
            confirmPasswordField.requestFocus();
            return;
        }
        if (!password.equals(confpassword)) {
            confirmPasswordField.setError("Konfirmasi password tidak sama");
            confirmPasswordField.requestFocus();
            return;
        }

        restAPIInterface apiInterface = Service.getRetrofitInstance().create(restAPIInterface.class);
        Call<userRegister> call = apiInterface.registerUser(username,nama,email,genre,password,confpassword);
        call.enqueue(new Callback<userRegister>() {
            @Override
            public void onResponse(Call<userRegister> call, Response<userRegister> response) {
                if (response.code() == 200) {
                    Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
//                  Toast.makeText(RegisterActivity.this, "Terjadi Kesalahan saat registrasi!", Toast.LENGTH_SHORT).show();
                    Log.e("RegisterActivity", "Terdapat error saat registrasi");
                    errorMessage(response);
                }
            }

            @Override
            public void onFailure(Call<userRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Terjadi Kesalahan!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void errorMessage(Response<userRegister> response){
        try{
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            JSONObject errorMessages = jObjError.getJSONObject("messages");

            //conditional di tiap error
            if(errorMessages.has("username")){
                String username = errorMessages.getString("username");
                usernameField.setError(username);
                usernameField.requestFocus();
            }

            if(errorMessages.has("email")){
                String email = errorMessages.getString("email");
                emailField.setError(email);
                emailField.requestFocus();
            }

            if(errorMessages.has("password")){
                String password = errorMessages.getString("password");
                passwordField.setError(password);
                passwordField.requestFocus();
            }

            if(errorMessages.has("confpassword")){
                String confpassword = errorMessages.getString("confpassword");
                confirmPasswordField.setError(confpassword);
                confirmPasswordField.requestFocus();
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}