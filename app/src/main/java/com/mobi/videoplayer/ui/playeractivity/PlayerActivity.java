package com.mobi.videoplayer.ui.playeractivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

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
import com.mobi.videoplayer.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PlayerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.player_view)
    PlayerView playerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;


    SimpleExoPlayer player;
    Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);

        video = getIntent().getParcelableExtra("video");
        toolbar.setTitle(video.getTitle());
        setToolbar(toolbar);
        setUpHome();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(this);
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
                    new DefaultHttpDataSourceFactory(Util.getUserAgent(getApplicationContext(), "video-player"));
            if (video.getUrl() != null) {
                HlsMediaSource hlsMediaSource =
                        new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(video.getUrl()));
                player.prepare(hlsMediaSource);
                player.setPlayWhenReady(true);
                playerView.setPlayer(player);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
        if (playerView != null) {
            playerView.onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player == null) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (playerView != null) {
            playerView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (playerView != null) {
            playerView.onPause();
        }
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}