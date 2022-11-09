package com.birol.bmusic.recycleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.birol.bmusic.MusicPlayerActivity;
import com.birol.bmusic.R;
import com.birol.bmusic.model.AudioModel;
import com.birol.bmusic.util.MyMediaPlayer;

import java.util.ArrayList;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder>{

    ArrayList<String> artistList;
    ArrayList<String> filteredList;
    Context context;

    public ArtistListAdapter(ArrayList<String> artistList, Context context) {
        this.artistList = artistList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_item_artist,parent,false);
        return new ArtistListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String artist = artistList.get(position);
        holder.titleTextView.setText(artist);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView,artistTextView,albumTextView;
        ImageView iconImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.artistTextview);

        }
    }

    public ArrayList<String> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(ArrayList<String> filteredList) {
        this.artistList = filteredList;
        notifyDataSetChanged();
    }
}
