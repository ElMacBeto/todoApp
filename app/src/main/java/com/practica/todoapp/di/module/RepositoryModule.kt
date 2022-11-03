package com.practica.todoapp.di.module

import com.practica.todoapp.data.repository.LocalRepository
import com.practica.todoapp.data.repository.WebRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideLocalRepository(): LocalRepository {
        return LocalRepository()
    }

    @Singleton
    @Provides
    fun provideWebRepository(): WebRepository {
        return WebRepository()
    }

}