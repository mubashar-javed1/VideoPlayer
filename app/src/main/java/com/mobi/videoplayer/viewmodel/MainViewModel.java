package com.mobi.videoplayer.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobi.videoplayer.data.Video;
import com.mobi.videoplayer.networkcall.ApiResponse;
import com.mobi.videoplayer.repository.Repository;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<Video> currentVideo = new MutableLiveData<>();

    MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getAllVideos() {
        disposables.add(repository.getVideos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.responseError(throwable))
                ));
    }

    public MutableLiveData<ApiResponse> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<Video> getVideo(int location) {
        currentVideo.setValue(Objects.requireNonNull(Objects.requireNonNull(responseLiveData.getValue()).data).get(location));
        return currentVideo;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}