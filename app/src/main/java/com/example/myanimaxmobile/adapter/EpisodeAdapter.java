package com.example.myanimaxmobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myanimaxmobile.R;
import com.example.myanimaxmobile.response.EpisodeModel;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeHolder> {
    List<EpisodeModel> listEpisode;
    LayoutInflater inflater;

    public EpisodeAdapter(Context context, List<EpisodeModel> listEpisode) {
        this.listEpisode = listEpisode;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_anime_episode, parent, false);
        return new EpisodeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.episode.setText("Episode "+listEpisode.get(position).getEps());
        holder.episodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = listEpisode.get(position).getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(android.net.Uri.parse(url));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listEpisode.size();
    }

    public class EpisodeHolder extends RecyclerView.ViewHolder {
        TextView episode;
        Button episodeButton;
        public EpisodeHolder(@NonNull View itemView) {
            super(itemView);
            episode = itemView.findViewById(R.id.episodeRow_rv);
            episodeButton = itemView.findViewById(R.id.episodeButton_rv);
        }
    }
}
