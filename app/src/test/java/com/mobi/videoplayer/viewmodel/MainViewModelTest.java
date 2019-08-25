package com.mobi.videoplayer.viewmodel;

import com.mobi.videoplayer.repository.Repository;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {
    @Mock
    Repository repository;

    @InjectMocks
    MainViewModel mainViewModel = new MainViewModel(repository);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        List<AcronymResponse> list = Collections.singletonList(new AcronymResponse());
        Observable<List<AcronymResponse>> observable = Observable.create(observer -> {
            observer.onNext(list);
            observer.onComplete();
        });
        Mockito.when(repository.getAcronyms(Mockito.any())).thenReturn(observable);
    }

    @After
    public void tearDown() {
    }

    @BeforeClass
    public static void setUpClass() {

        // Override the default "out of the box" AndroidSchedulers.mainThread() Scheduler
        //
        // This is necessary here because otherwise if the static initialization block in AndroidSchedulers
        // is executed before this, then the Android SDK dependent version will be provided instead.
        //
        // This would cause a java.lang.ExceptionInInitializerError when running the test as a
        // Java JUnit test as any attempt to resolve the default underlying implementation of the
        // AndroidSchedulers.mainThread() will fail as it relies on unavailable Android dependencies.

        // Comment out this line to see the java.lang.ExceptionInInitializerError
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Test
    public void getAbbreviationResultTest() {
        mainViewModel.getAbbreviationResult("TV");
        Mockito.verify(repository).getAcronyms(Mockito.any());
    }

    @Test
    public void getResponseLiveData() {

    }
}