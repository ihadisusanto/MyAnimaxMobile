package com.example.myanimaxmobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myanimaxmobile.Activity.AnimeDetailsActivity;
import com.example.myanimaxmobile.R;
import com.example.myanimaxmobile.response.AnimeModel;
import com.example.myanimaxmobile.utils.DownloadPoster;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeHolder> {
    List<AnimeModel> listAnime;
    LayoutInflater inflater;

    public AnimeAdapter(Context context, List<AnimeModel> listAnime) {
        this.listAnime = listAnime;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AnimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_anime_list, parent, false);
        return new AnimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeHolder holder, @SuppressLint("RecyclerView") int position){
        holder.namaAnime.setText(listAnime.get(position).getTitle_japan());
        holder.genreAnime.setText(listAnime.get(position).getGenre());
        holder.ratingAnime.setText(listAnime.get(position).getMal_score());
        new DownloadPoster(holder.posterAnime).execute(listAnime.get(position).getPoster());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AnimeDetailsActivity.class);
                Bundle extras = new Bundle();
                extras.putString("ID", listAnime.get(position).getId());
                extras.putString("TITLE_JP", listAnime.get(position).getTitle_japan());
                extras.putString("TITLE_EN", listAnime.get(position).getTitle_english());
                extras.putString("GENRE", listAnime.get(position).getGenre());
                extras.putString("MAL_SCORE", listAnime.get(position).getMal_score());
                extras.putString("POSTER", listAnime.get(position).getPoster());
                extras.putString("DESCRIPTION", listAnime.get(position).getDescription());
                extras.putString("EPISODE", listAnime.get(position).getEpisode());
                extras.putString("STATUS", listAnime.get(position).getStatus());
                extras.putString("RATING_AGE", listAnime.get(position).getRating_age());
                extras.putString("YEAR", listAnime.get(position).getYear());
                intent.putExtras(extras);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAnime.size();
    }

    public class AnimeHolder extends RecyclerView.ViewHolder{
        TextView namaAnime, genreAnime, ratingAnime;
        ImageView posterAnime;
        public AnimeHolder(@NonNull View itemView) {
            super(itemView);
            namaAnime = itemView.findViewById(R.id.namaAnime_rv);
            genreAnime = itemView.findViewById(R.id.genreAnime_rv);
            ratingAnime = itemView.findViewById(R.id.ratingAnime_rv);
            posterAnime = itemView.findViewById(R.id.posterAnime_rv);
        }
    }
}

