package pl.silsense.fboxtester.session;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SessionRepositoryModule {

    @Provides
    @Singleton
    public SessionRepository provideSessionRepository(@ApplicationContext Context context) {
        return new SessionRepository(context);
    }
}
