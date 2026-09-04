package com.tranduytruong.novatech.di

import com.tranduytruong.novatech.core.data.repository.AuthRepositoryImpl
import com.tranduytruong.novatech.core.data.repository.CartRepositoryImpl
import com.tranduytruong.novatech.core.data.repository.ProductRepositoryImpl
import com.tranduytruong.novatech.core.domain.repository.AuthRepository
import com.tranduytruong.novatech.core.domain.repository.CartRepository
import com.tranduytruong.novatech.core.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindProductRepository(implementation: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(implementation: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(implementation: AuthRepositoryImpl): AuthRepository
}
