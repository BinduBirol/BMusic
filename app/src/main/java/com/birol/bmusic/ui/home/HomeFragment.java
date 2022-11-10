package com.birol.bmusic.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birol.bmusic.MainActivity;
import com.birol.bmusic.databinding.FragmentHomeBinding;
import com.birol.bmusic.model.AudioModel;
import com.birol.bmusic.recycleview.ArtistListAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ArrayList<AudioModel> songsList =new ArrayList<AudioModel>();
    private ArtistListAdapter adapter;
    private ArrayList<String> artistList= new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        MainActivity mainActivity = (MainActivity) getActivity();
        RecyclerView recyclerView = binding.recyclerViewArtist;
        TextView noMusicTextView=binding.noSongsText;

        //songsList= mainActivity.getSongsList();
        artistList= mainActivity.getArtistList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        HashSet<String> hset = new HashSet<String>(artistList);
        artistList= new ArrayList<String>(hset);
        artistList.sort(String::compareToIgnoreCase);
        adapter = new ArtistListAdapter(artistList,getContext());

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if(artistList.size()==0){
                    noMusicTextView.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        SearchView searchView = binding.searchViewArtist;
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });
        return root;
    }

    private void filterList(String s) {
        List<String> filteredList= new ArrayList<String>();
        for(String c: artistList){
            if(c.toLowerCase().contains(s.toLowerCase())){
                filteredList.add(c);
            }
        }
        if(filteredList.size()==0){
            MainActivity mainActivity = (MainActivity) getActivity();
            Toast.makeText(mainActivity,"Could not find the artist!",Toast.LENGTH_SHORT);
        }else {
            adapter.setFilteredList((ArrayList<String>) filteredList);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}