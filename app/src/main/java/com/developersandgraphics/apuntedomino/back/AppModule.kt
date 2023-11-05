package com.developersandgraphics.apuntedomino.back

import android.content.Context
import com.developersandgraphics.apuntedomino.ApunteDominoApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext app: Context): ApunteDominoApp {
        return app as ApunteDominoApp
    }
}