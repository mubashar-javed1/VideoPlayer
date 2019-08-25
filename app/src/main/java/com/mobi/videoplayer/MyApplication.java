package com.mobi.videoplayer;

import android.app.Application;

import com.mobi.videoplayer.di.component.AppComponent;
import com.mobi.videoplayer.di.component.DaggerAppComponent;
import com.mobi.videoplayer.di.module.AppModule;
import com.mobi.videoplayer.di.module.RetrofitModule;

public class MyApplication extends Application {
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).retrofitModule(new RetrofitModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}