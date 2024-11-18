package com.example.githubrepos.di

import android.content.Context
import com.example.githubrepos.data.db.dao.RepoDao
import com.example.githubrepos.data.impl.UserRepositoryImpl
import com.example.githubrepos.data.mappers.ReposMapper
import com.example.githubrepos.data.network.ApiInterface
import com.example.githubrepos.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        @ApplicationContext appContext: Context,
        apiInterface: ApiInterface,
        mapper: ReposMapper,
        reposDao: RepoDao,
    ): UserRepository = UserRepositoryImpl(
        context = appContext,
        apiInterface = apiInterface,
        mapper = mapper,
        reposDao = reposDao,
    )

    @Provides
    @Singleton
    fun provideReposMapper() = ReposMapper()

}