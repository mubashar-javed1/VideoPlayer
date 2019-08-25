package com.mobi.videoplayer.ui.playeractivity;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mobi.videoplayer.R;
import com.mobi.videoplayer.data.Video;
import com.mobi.videoplayer.viewmodel.MainViewModel;
import com.mobi.videoplayer.viewmodel.ViewModelFactory;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.player_view)
    PlayerView playerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private int location = -1;
    private Unbinder unbinder;
    private SimpleExoPlayer player;
    private MainViewModel viewModel;
    private Video video;


    public PlayerFragment() {
        // Required empty public constructor
    }

    public PlayerFragment(int loc) {
        this.location = loc;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        unbinder = ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        video = viewModel.getVideo(location).getValue();
        assert video != null;
        tvTitle.setText(video.getTitle());

        return view;
    }

    private void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(Objects.requireNonNull(getActivity()));
            player.addListener(new Player.EventListener() {
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    switch (playbackState) {

                        case Player.STATE_BUFFERING:
                            if (progressBar != null) {
                                progressBar.setVisibility(VISIBLE);
                            }
                            break;

                        case Player.STATE_ENDED:
                            player.seekTo(0);
                            break;

                        case Player.STATE_IDLE:
                            break;

                        case Player.STATE_READY:
                            if (progressBar != null) {
                                progressBar.setVisibility(GONE);
                            }
                            break;

                        default:
                            break;
                    }
                }
            });

            DataSource.Factory dataSourceFactory =
                    new DefaultHttpDataSourceFactory(Util.getUserAgent(getActivity(), "video-player"));
            if (video.getUrl() != null) {
                HlsMediaSource hlsMediaSource =
                        new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(video.getUrl()));
                player.prepare(hlsMediaSource);
                //player.setPlayWhenReady(true);
                playerView.setPlayer(player);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (player != null) {
            playerView.onResume();
        } else initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            playerView.onResume();
            player.setPlayWhenReady(true);
            player.getPlaybackState();
        } else initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (playerView != null) {
            playerView.onPause();
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (playerView != null) {
            playerView.onPause();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

}
