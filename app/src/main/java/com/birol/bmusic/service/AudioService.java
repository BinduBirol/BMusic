package com.birol.bmusic.service;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.birol.bmusic.model.AudioModel;

import java.io.File;
import java.util.ArrayList;

public class AudioService {



    public ArrayList<AudioModel> getAllSongs(Context c){
        ArrayList<AudioModel> songsList = new ArrayList<AudioModel>();
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

        Cursor cursor = c.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,"column_name ASC");
        while(cursor.moveToNext()){
            AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            if(new File(songData.getPath()).exists()){
                songsList.add(songData);
            }
        }
        return songsList;
    }

    public ArrayList<String> getartistList(Context c){
        ArrayList<String> artistList = new ArrayList<String>();
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

        Cursor cursor = c.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,"column_name ASC");
        while(cursor.moveToNext()){
            AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            if(new File(songData.getPath()).exists()){
                artistList.add(songData.getArtist());
            }
        }
        return artistList;
    }
}
