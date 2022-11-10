package com.birol.bmusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birol.bmusic.databinding.ActivityArtistViewScrollingBinding;
import com.birol.bmusic.model.AudioModel;
import com.birol.bmusic.recycleview.AlbumListAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ArtistViewScrollingActivity extends AppCompatActivity {

    private ActivityArtistViewScrollingBinding binding;
    ArrayList<AudioModel> songsList =new ArrayList<AudioModel>();
    private ArrayList<String> artistList = new ArrayList<String>();
    private AlbumListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityArtistViewScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String artist = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            artist = extras.getString("artist_name");
        }
        binding.toolbarLayout.setTitle(artist);

        if(checkPermission() == false){
            requestPermission();
            return;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST_ID,
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,"TITLE ASC");
        while(cursor.moveToNext()){
            AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            if(new File(songData.getPath()).exists()){
                this.songsList.add(songData);
            }
        }

        String finalArtist = artist;
        ArrayList<AudioModel> filteredsongList= (ArrayList<AudioModel>) songsList.stream().filter(article -> article.getArtist().equals(finalArtist)).collect(Collectors.toList());
        ArrayList<String> albums= new ArrayList<String>();

        for(AudioModel s: filteredsongList)albums.add(s.getAlbum());

        HashSet<String> hset = new HashSet<String>(albums);
        albums= new ArrayList<String>(hset);
        //filteredsongList.stream().sorted(Comparator.comparing(AudioModel::getArtist)).collect(Collectors.toList());


        RecyclerView recyclerView = findViewById(R.id.artistrecycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Log.i("bindu", String.valueOf(albums.size()));
        adapter= new AlbumListAdapter(albums,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(ArtistViewScrollingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(ArtistViewScrollingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(ArtistViewScrollingActivity.this,"READ PERMISSION IS REQUIRED,PLEASE ALLOW FROM SETTTINGS",Toast.LENGTH_SHORT).show();
        }else
            ActivityCompat.requestPermissions(ArtistViewScrollingActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
    }
}