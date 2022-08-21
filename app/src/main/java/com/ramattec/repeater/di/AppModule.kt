package com.ramattec.repeater.di

import android.content.Context
import com.ramattec.data.local.MyPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): MyPreferenceManager {
        return MyPreferenceManager(context)
    }

}