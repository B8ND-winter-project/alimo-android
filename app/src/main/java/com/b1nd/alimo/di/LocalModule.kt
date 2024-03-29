package com.b1nd.alimo.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.b1nd.alimo.data.local.dao.ExampleDao
import com.b1nd.alimo.data.local.dao.FirebaseTokenDao
import com.b1nd.alimo.data.local.dao.TokenDao
import com.b1nd.alimo.data.local.database.AlimoDataBase
import com.b1nd.alimo.presentation.utiles.Env
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideAlimoDataBase(
        @ApplicationContext context: Context
    ): AlimoDataBase = Room
        .databaseBuilder(
            context,
            AlimoDataBase::class.java,
            Env.DATABASE
        )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideExampleDao(
        alimoDataBase: AlimoDataBase
    ): ExampleDao = alimoDataBase.exampleDao()

    @Provides
    @Singleton
    fun provideFirebaseTokenDao(
        alimoDataBase: AlimoDataBase
    ): FirebaseTokenDao = alimoDataBase.firebaseTokenDao()

    @Provides
    @Singleton
    fun provideTokenDao(
        alimoDataBase: AlimoDataBase
    ): TokenDao = alimoDataBase.tokenDao()

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context
        .getSharedPreferences("alimo_prefs", Context.MODE_PRIVATE)

}