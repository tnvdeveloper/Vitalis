package com.tnvdeveloper.vitalis.di

import android.content.Context
import androidx.room.Room
import com.tnvdeveloper.vitalis.data.local.AppDatabase
import com.tnvdeveloper.vitalis.data.local.BloodPressureDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "vitalis_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBloodPressureDao(database: AppDatabase): BloodPressureDao {
        return database.bloodPressureDao()
    }
}
