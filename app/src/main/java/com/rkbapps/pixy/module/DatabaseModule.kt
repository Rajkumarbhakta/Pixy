package com.rkbapps.pixy.module

import android.content.Context
import androidx.room.Room
import com.rkbapps.pixy.db.ImageDao
import com.rkbapps.pixy.db.ImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(imageDatabase: ImageDatabase): ImageDao {
        return imageDatabase.imageDao()
    }

    @Provides
    @Singleton
    fun provideImageDatabase(@ApplicationContext context: Context): ImageDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ImageDatabase::class.java,
            "images"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


}