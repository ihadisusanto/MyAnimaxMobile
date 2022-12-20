package com.example.myanimaxmobile.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myanimaxmobile.R;
import com.example.myanimaxmobile.adapter.AnimeAdapter;
import com.example.myanimaxmobile.request.Service;
import com.example.myanimaxmobile.request.restAPIInterface;
import com.example.myanimaxmobile.response.AnimeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private String keyword,token;
    private TextView keywordView;
    private List<AnimeModel> listAnime;
    private SharedPreferences sharedPreferences;
    private AnimeAdapter animeAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN", null);
        Intent intent = getIntent();
        keyword = intent.getStringExtra("KEYWORD");
        keywordView = findViewById(R.id.keywordView);
        keywordView.setText(keyword);
        listAnime = new ArrayList<>();
        recyclerView = findViewById(R.id.searchAnime_rv);
        linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Query Pencarian Anime
        restAPIInterface apiInterface = Service.getRetrofitInstance().create(restAPIInterface.class);
        Call<List<AnimeModel>> call = apiInterface.searchAnime("Bearer "+token, keyword);
        call.enqueue(new Callback<List<AnimeModel>>(){
            @Override
            public void onResponse(Call<List<AnimeModel>> call, Response<List<AnimeModel>> response) {
                if(response.code() == 200){     //Jika Response Sukses
                    Log.e("Search", "Sukses");
                    List<AnimeModel> animeModels = response.body();
                    for(AnimeModel animeModel : animeModels){
                        listAnime.add(animeModel);
                    }
                }
                animeAdapter = new AnimeAdapter(SearchActivity.this, listAnime);
                recyclerView.setAdapter(animeAdapter);
            }

            @Override
            public void onFailure(Call<List<AnimeModel>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Anime tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}