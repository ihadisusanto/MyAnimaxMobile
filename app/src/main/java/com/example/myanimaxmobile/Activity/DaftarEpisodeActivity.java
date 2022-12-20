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
import com.example.myanimaxmobile.adapter.EpisodeAdapter;
import com.example.myanimaxmobile.request.Service;
import com.example.myanimaxmobile.request.restAPIInterface;
import com.example.myanimaxmobile.response.EpisodeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarEpisodeActivity extends AppCompatActivity {
    private String token;
    private List<EpisodeModel> listEpisode;
    private EpisodeAdapter episodeAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TextView judulAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_episode);
        SharedPreferences sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
        token = sharedPreferences.getString("TOKEN",null);
        judulAnime = findViewById(R.id.namaAnimeEpisode);
        listEpisode = new ArrayList<>();
        recyclerView = findViewById(R.id.episodeList_rv);
        linearLayoutManager = new LinearLayoutManager(DaftarEpisodeActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String id = extras.getString("ID");
        judulAnime.setText(extras.getString("NAMA"));
        restAPIInterface apiInterface = Service.getRetrofitInstance().create(restAPIInterface.class);
        Call<List<EpisodeModel>> call = apiInterface.getEpisode("Bearer "+token, id);
        call.enqueue(new Callback<List<EpisodeModel>>(){
            @Override
            public void onResponse(Call<List<EpisodeModel>> call, Response<List<EpisodeModel>> response) {
                if(response.code() == 200){
                    List<EpisodeModel> episodeModels = response.body();
                    for(EpisodeModel episodeModel : episodeModels){
                       listEpisode.add(episodeModel);
                       Log.e("Episode", episodeModel.getAnime_id());
                       Log.e("Episode", episodeModel.getEps());
                    }
                    episodeAdapter = new EpisodeAdapter(DaftarEpisodeActivity.this, listEpisode);
                    recyclerView.setAdapter(episodeAdapter);
                }
                else{
                    Toast.makeText(DaftarEpisodeActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EpisodeModel>> call, Throwable t) {
                Toast.makeText(DaftarEpisodeActivity.this, "Gagal Memuat Episode Anime", Toast.LENGTH_SHORT).show();
            }
        });

    }
}