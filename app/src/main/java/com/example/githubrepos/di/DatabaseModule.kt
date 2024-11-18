package com.example.githubrepos.di

import android.content.Context
import androidx.room.Room
import com.example.githubrepos.data.db.AppDatabase
import com.example.githubrepos.data.db.dao.RepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "github_users_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): RepoDao {
        return appDatabase.repositoryDao()
    }

}