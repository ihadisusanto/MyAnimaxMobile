package com.example.myanimaxmobile.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.example.myanimaxmobile.R;
import com.example.myanimaxmobile.adapter.AnimeAdapter;
import com.example.myanimaxmobile.request.Service;
import com.example.myanimaxmobile.request.restAPIInterface;
import com.example.myanimaxmobile.response.AnimeModel;
import com.example.myanimaxmobile.Activity.LoginActivity;
import com.google.android.material.textfield.*;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    private TextView namaPengguna;
    private RecyclerView recyclerView;
    private AnimeAdapter animeAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<AnimeModel> listAnime;
    private Button searchButton;
    private TextInputLayout searchBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        listAnime = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_animeList);
        linearLayoutManager = new LinearLayoutManager(DashboardActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchButton = findViewById(R.id.searchButton);
        searchBox = findViewById(R.id.searchBox);
        SharedPreferences  sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);

        if(!sharedPreferences.contains("TOKEN")) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }else{
            namaPengguna = findViewById(R.id.namaUser);
            namaPengguna.setText(sharedPreferences.getString("NAME",null));
            restAPIInterface apiInterface = Service.getRetrofitInstance().create(restAPIInterface.class);
            Call<List<AnimeModel>> call = apiInterface.getAnime("Bearer "+sharedPreferences.getString("TOKEN",null));
            call.enqueue(new Callback<List<AnimeModel>>(){
                @Override
                public void onResponse(Call<List<AnimeModel>> call, Response<List<AnimeModel>> response) {
                    for(AnimeModel animemodel : response.body()){
                        listAnime.add(animemodel);
                        Log.e("Recycler View", "nama anime : "+animemodel.getTitle_japan());
                    }
                    animeAdapter = new AnimeAdapter(DashboardActivity.this,listAnime);
                    recyclerView.setAdapter(animeAdapter);
                }

                @Override
                public void onFailure(Call<List<AnimeModel>> call, Throwable t) {
                    Toast.makeText(DashboardActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            });
            searchButton.setOnClickListener(v -> {
                Intent i = new Intent(DashboardActivity.this, SearchActivity.class);
                i.putExtra("KEYWORD",searchBox.getEditText().getText().toString());
                startActivity(i);
                searchBox.getEditText().setText("");
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profileMenu:
                Intent profile = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(profile);
                return true;
            case R.id.changePasswordMenu:
                Intent changePassword = new Intent(DashboardActivity.this, ChangePasswordActivity.class);
                startActivity(changePassword);
                return true;
            case R.id.logoutMenu:
                SharedPreferences sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}