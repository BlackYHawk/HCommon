package com.hawk.base.module;


import android.support.v7.app.AppCompatActivity;

import com.hawk.base.qualifier.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lan on 2016/6/29.
 */
@Module
public class ActModule {
    private final AppCompatActivity activity;

    public ActModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides @ActivityScope
    public AppCompatActivity provideActivity() {
        return activity;
    }

}
