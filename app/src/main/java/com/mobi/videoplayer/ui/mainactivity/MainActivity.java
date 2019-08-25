package com.mobi.videoplayer.ui.mainactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.mobi.videoplayer.MyApplication;
import com.mobi.videoplayer.R;
import com.mobi.videoplayer.adapter.VideoFragmentAdapter;
import com.mobi.videoplayer.data.Video;
import com.mobi.videoplayer.networkcall.ApiResponse;
import com.mobi.videoplayer.ui.BaseActivity;
import com.mobi.videoplayer.viewmodel.MainViewModel;
import com.mobi.videoplayer.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.rv_videos)
    ViewPager2 rvVideos;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    MainViewModel viewModel;
    VideoFragmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((MyApplication) getApplication()).getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getResponseLiveData().observe(this, this::consumeResponse);

        if (savedInstanceState == null) {
            if (!checkNetworkAvailability()) {
                showStatusText(getString(R.string.network_error));
            } else
                viewModel.getAllVideos();
        }
    }


    private void consumeResponse(ApiResponse response) {
        switch (response.status) {
            case LOADING:
                showProgressBar();
                break;

            case SUCCESS:
                assert response.data != null;
                updateRecyclerView(response.data);
                break;

            case ERROR:
                if (response.error != null)
                    showStatusText(response.error.getMessage());
                break;

            default:

        }
    }

    private void updateRecyclerView(List<Video> videos) {
        if (videos != null && !videos.isEmpty()) {
            showRecyclerView();
            rvVideos.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            rvVideos.setOffscreenPageLimit(1);
            adapter = new VideoFragmentAdapter(getSupportFragmentManager(), getLifecycle(), videos);
            rvVideos.setAdapter(adapter);
        } else {
            showStatusText(getString(R.string.no_result_found));
        }
    }

    protected void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        tvStatus.setVisibility(View.GONE);
        rvVideos.setVisibility(View.GONE);
    }

    protected void showStatusText(String text) {
        progressBar.setVisibility(View.GONE);
        rvVideos.setVisibility(View.GONE);
        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(text);
    }

    protected void showRecyclerView() {
        progressBar.setVisibility(View.GONE);
        tvStatus.setVisibility(View.GONE);
        rvVideos.setVisibility(View.VISIBLE);
    }
}