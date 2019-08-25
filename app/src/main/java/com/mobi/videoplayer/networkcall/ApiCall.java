package com.mobi.videoplayer.networkcall;

import com.mobi.videoplayer.data.Video;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiCall {
    @GET(ApiConfig.GET_VIDEOS)
    Observable<List<Video>> getVideos();
}