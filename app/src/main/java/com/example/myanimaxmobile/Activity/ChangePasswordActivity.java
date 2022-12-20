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

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText oldPass, newPass, confirmPass;
    private Button updatePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        oldPass = findViewById(R.id.oldPass);
        newPass = findViewById(R.id.newPass);
        confirmPass = findViewById(R.id.confirmPass);
        updatePass = findViewById(R.id.updatePassButton);

        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(oldPass,newPass,confirmPass);
            }
        });
    }

    public void changePassword(EditText oldPass, EditText newPass, EditText confirmPass){
        SharedPreferences sharedPreferences = getSharedPreferences("USER_LOGIN",MODE_PRIVATE);
        String token = "Bearer "+sharedPreferences.getString("TOKEN",null);
        String username = sharedPreferences.getString("USERNAME",null);
        String oldPassword = oldPass.getText().toString();
        String newPassword = newPass.getText().toString();
        String confirmPassword = confirmPass.getText().toString();
        if(oldPassword.isEmpty()){
            oldPass.setError("Password lama tidak boleh kosong");
            oldPass.requestFocus();
            return;
        }
        if(newPassword.isEmpty()){
            newPass.setError("Password baru tidak boleh kosong");
            newPass.requestFocus();
            return;
        }
        if(confirmPassword.isEmpty()){
            confirmPass.setError("Konfirmasi password tidak boleh kosong");
            confirmPass.requestFocus();
            return;
        }
        if(!newPassword.equals(confirmPassword)){
            confirmPass.setError("Konfirmasi password tidak sama");
            confirmPass.requestFocus();
            return;
        }
        dummyPassword body = new dummyPassword(oldPassword,newPassword,confirmPassword);
        restAPIInterface restAPIInterface = Service.getRetrofitInstance().create(com.example.myanimaxmobile.request.restAPIInterface.class);
        Call<userUpdate> call = restAPIInterface.updatePassword(token,username,body);
        call.enqueue(new Callback<userUpdate>() {
            @Override
            public void onResponse(Call<userUpdate> call, Response<userUpdate> response) {
                if(response.body().getStatus().equals("201")){
                    Toast.makeText(ChangePasswordActivity.this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePasswordActivity.this, DashboardActivity.class);
                    startActivity(intent);
                }else if(response.body().getStatus().equals("202")){
                    Toast.makeText(ChangePasswordActivity.this, "Password lama salah", Toast.LENGTH_SHORT).show();
                    oldPass.requestFocus();
                }else if(response.body().getStatus().equals("203")){
                    Toast.makeText(ChangePasswordActivity.this, "Password baru tidak cocok", Toast.LENGTH_SHORT).show();
                    confirmPass.requestFocus();
                }
                else{
                    Toast.makeText(ChangePasswordActivity.this, "Terjadi Kesalahan. Password Gagal Diubah", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<userUpdate> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Password gagal diubah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class dummyPassword {
        private String oldpass;
        private String newpass;
        private String confpassword;

        public dummyPassword(String oldPass, String newPass, String confirmPass){
            this.oldpass = oldPass;
            this.newpass = newPass;
            this.confpassword = confirmPass;
        }
    }
}