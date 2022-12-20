package com.example.myanimaxmobile.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myanimaxmobile.R;
import com.example.myanimaxmobile.utils.DownloadPoster;

public class AnimeDetailsActivity extends AppCompatActivity {
    private TextView titleJp, titleEn, year, genre, status, episode,rating_age, mal_score, description;
    private ImageView poster;
    private Button btnListEpisode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_details);
        //SharedPreferences sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);

        titleJp = findViewById(R.id.judulJPNDetails);
        titleEn = findViewById(R.id.judulEngDetails);
        year = findViewById(R.id.yearDetails);
        genre = findViewById(R.id.genreDetails);
        status = findViewById(R.id.statusDetails);
        episode = findViewById(R.id.episodeDetails);
        rating_age = findViewById(R.id.ratingAgeDetails);
        mal_score = findViewById(R.id.malScoreDetails);
        description = findViewById(R.id.deskripsiDetails);
        poster = findViewById(R.id.posterDetails);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        titleJp.setText(extras.getString("TITLE_JP"));
        titleEn.setText(extras.getString("TITLE_EN"));
        year.setText(extras.getString("YEAR"));
        genre.setText(extras.getString("GENRE"));
        status.setText(extras.getString("STATUS"));
        episode.setText(extras.getString("EPISODE"));
        rating_age.setText(extras.getString("RATING_AGE"));
        mal_score.setText(extras.getString("MAL_SCORE"));
        description.setText(extras.getString("DESCRIPTION"));

        new DownloadPoster(poster).execute(extras.getString("POSTER"));
        btnListEpisode = findViewById(R.id.daftarEpisodeButton);
        btnListEpisode.setOnClickListener(v -> {
            Intent intent1 = new Intent(AnimeDetailsActivity.this, DaftarEpisodeActivity.class);
            Bundle extras1 = new Bundle();
            extras1.putString("ID", extras.getString("ID"));
            extras1.putString("NAMA", extras.getString("TITLE_JP"));
            intent1.putExtras(extras1);
            startActivity(intent1);
        });

    }
}