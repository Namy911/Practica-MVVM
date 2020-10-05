package com.example.practica.di

import android.content.Context
import androidx.room.Room
import com.example.practica.data.db.PrepopulateDatabase
import com.example.practica.data.db.TaskDataBase
import com.example.practica.data.db.TaskSchema
import com.example.practica.data.db.migration.Migration1To2
import com.example.practica.data.entity.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, TaskDataBase::class.java, TaskSchema.DB_NAME )
            .addMigrations(Migration1To2())
            .addCallback(PrepopulateDatabase())
            .build()

    @Singleton
    @Provides
    fun provideRosterRepository(db: TaskDataBase) = db.userSore()

    @Singleton
    @Provides
    fun provideArticleRepository(db: TaskDataBase) = db.articleSore()

    @Singleton
    @Provides
    fun provideCategoryRepository(db: TaskDataBase) = db.categorySore()

    @Singleton
    @Provides
    fun provideUserAndArticleRepository(db: TaskDataBase) = db.userAndArticleSore()

    @Singleton
    @Provides
    fun provideCategoryAndArticleRepository(db: TaskDataBase) = db.categoryAndArticleSore()

}