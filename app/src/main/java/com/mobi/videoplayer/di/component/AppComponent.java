package com.mobi.videoplayer.di.component;

import com.mobi.videoplayer.di.module.AppModule;
import com.mobi.videoplayer.di.module.RetrofitModule;
import com.mobi.videoplayer.ui.mainactivity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, RetrofitModule.class})
@Singleton
public interface AppComponent {
    void doInjection(MainActivity mainActivity);
}