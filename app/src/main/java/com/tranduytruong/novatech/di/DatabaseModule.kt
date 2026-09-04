package com.tranduytruong.novatech.di

import android.content.Context
import androidx.room.Room
import com.tranduytruong.novatech.core.data.local.NovaTechDatabase
import com.tranduytruong.novatech.core.data.local.dao.CartDao
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
    fun provideDatabase(@ApplicationContext context: Context): NovaTechDatabase =
        Room.databaseBuilder(
            context,
            NovaTechDatabase::class.java,
            "novatech.db",
        ).build()

    @Provides
    fun provideCartDao(database: NovaTechDatabase): CartDao = database.cartDao()
}
