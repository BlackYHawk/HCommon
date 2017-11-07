package com.hawk.base.model.util;

import android.content.Context;

import com.hawk.base.model.util.impl.CharacterParser;
import com.hawk.base.model.util.impl.PreferenceParser;
import com.hawk.base.model.util.impl.ResParser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lan on 2016/4/14.
 */
@Module
public class UtilModule {

    @Provides @Singleton
    public CharacterDelegate provideCharacterParser() {
        return new CharacterParser();
    }

    @Provides @Singleton
    public ResDelegate provideStringFetcher(Context context) {
        return new ResParser(context);
    }

    @Provides @Singleton
    public PreferenceDelegate providePreferenceParser(Context context) {
        return new PreferenceParser(context);
    }

}
