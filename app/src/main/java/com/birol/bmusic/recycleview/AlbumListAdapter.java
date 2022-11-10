package com.birol.bmusic.recycleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.birol.bmusic.ArtistViewScrollingActivity;
import com.birol.bmusic.R;

import java.util.ArrayList;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.ViewHolder>{

    ArrayList<String> albumList;
    ArrayList<String> filteredList;
    Context context;

    public AlbumListAdapter(ArrayList<String> albumList, Context context) {
        this.albumList = albumList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_albumxml,parent,false);
        return new AlbumListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String artist = albumList.get(position);
        holder.titleTextView.setText(artist);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return albumList.size();
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
        this.albumList = filteredList;
        notifyDataSetChanged();
    }
}
