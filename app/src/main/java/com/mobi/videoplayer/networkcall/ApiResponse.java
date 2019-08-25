package com.mobi.videoplayer.networkcall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobi.videoplayer.data.Video;

import java.util.List;

import static com.mobi.videoplayer.networkcall.Status.ERROR;
import static com.mobi.videoplayer.networkcall.Status.LOADING;
import static com.mobi.videoplayer.networkcall.Status.SUCCESS;

public class ApiResponse {

    public final Status status;

    @Nullable
    public final List<Video> data;

    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable List<Video> data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull List<Video> data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse responseError(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }
}