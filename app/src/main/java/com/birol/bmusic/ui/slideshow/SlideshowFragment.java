package com.birol.bmusic.ui.slideshow;

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
import com.birol.bmusic.databinding.FragmentSlideshowBinding;
import com.birol.bmusic.model.AudioModel;
import com.birol.bmusic.recycleview.MusicListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    ArrayList<AudioModel> songsList =new ArrayList<AudioModel>();
    private MusicListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //final TextView textView = binding.textHome;
        MainActivity mainActivity = (MainActivity) getActivity();
        RecyclerView recyclerView = binding.recyclerView;
        TextView noMusicTextView=binding.noSongsText;

        songsList= mainActivity.getSongsList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MusicListAdapter(songsList,getContext());
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if(songsList.size()==0){
                    noMusicTextView.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        SearchView searchView = binding.searchView;
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
        List<AudioModel> filteredList= new ArrayList<AudioModel>();
        for(AudioModel c: songsList){
            if(c.getTitle().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(c);
            }
        }
        if(filteredList.size()==0){
            MainActivity mainActivity = (MainActivity) getActivity();
            Toast.makeText(mainActivity,"Could not find the song!",Toast.LENGTH_SHORT);
        }else {
            adapter.setFilteredList((ArrayList<AudioModel>) filteredList);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}