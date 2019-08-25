package com.mobi.videoplayer.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mobi.videoplayer.data.Video;
import com.mobi.videoplayer.ui.playeractivity.PlayerFragment;

import java.util.ArrayList;
import java.util.List;

public class VideoFragmentAdapter extends FragmentStateAdapter {

    private List<Video> allVideos;
    private List<Fragment> allFragments;

    public VideoFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Video> allVideos) {
        super(fragmentManager, lifecycle);
        this.allVideos = allVideos;
        allFragments = new ArrayList<>();
        for (int i= 0; i<allVideos.size(); i++) {
            allFragments.add(new PlayerFragment(i));
        }
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return allFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return allVideos.size();
    }
}
