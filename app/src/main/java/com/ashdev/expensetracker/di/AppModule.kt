package com.ashdev.expensetracker.di

import android.content.Context
import androidx.room.Room

import com.ashdev.expensetracker.ExpenseTrackerApplication
import com.ashdev.expensetracker.database.AppDatabase
import com.ashdev.expensetracker.database.ExpenseInfoDao
import com.ashdev.expensetracker.database.PinInfoDao
import com.ashdev.expensetracker.prefsHelper.EncryptedPreferenceHelperImpl
import com.ashdev.expensetracker.prefsHelper.IEncryptedPreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(): Context {
        return ExpenseTrackerApplication.appContext
    }
    @Provides
    @Singleton
    fun provideIEncryptedPreferenceHelper(preferenceHelperImpl: EncryptedPreferenceHelperImpl): IEncryptedPreferenceHelper {
        return preferenceHelperImpl
    }

    @Provides
    @Singleton
    fun provideEncryptedPreferenceHelperImpl(context: Context): EncryptedPreferenceHelperImpl {
        return EncryptedPreferenceHelperImpl(context)
    }

    @Singleton
    @Provides
    fun getExpenseInfoDao(appDatabase: AppDatabase):ExpenseInfoDao {
        return appDatabase.getExpenseInfoDao()
    }

    @Singleton
    @Provides
    fun getPinInfoDao(appDatabase: AppDatabase): PinInfoDao {
        return appDatabase.getPinInfoDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.Meta.DATABASE_NAME)
            .fallbackToDestructiveMigration(true)
            .build()
    }
}