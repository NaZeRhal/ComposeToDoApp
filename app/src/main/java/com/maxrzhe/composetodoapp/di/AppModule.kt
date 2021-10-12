package com.maxrzhe.composetodoapp.di

import android.content.Context
import androidx.room.Room
import com.maxrzhe.composetodoapp.core.util.Constants
import com.maxrzhe.composetodoapp.data.local.ToDoDao
import com.maxrzhe.composetodoapp.data.local.ToDoDatabase
import com.maxrzhe.composetodoapp.data.repository.ToDoRepositoryImpl
import com.maxrzhe.composetodoapp.domain.repository.ToDoRepository
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
    fun provideDatabase(@ApplicationContext context: Context): ToDoDatabase =
        Room.databaseBuilder(context, ToDoDatabase::class.java, Constants.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideToDoDao(database: ToDoDatabase): ToDoDao = database.toDoDao

    @Singleton
    @Provides
    fun provideRepository(toDoDao: ToDoDao): ToDoRepository = ToDoRepositoryImpl(toDoDao)
}