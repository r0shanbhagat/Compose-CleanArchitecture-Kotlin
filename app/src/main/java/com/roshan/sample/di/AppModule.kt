package com.roshan.sample.di

import com.roshan.sample.data.respository.RepositoryImpl
import com.roshan.sample.domain.usecase.GetProductDetailUseCase
import com.roshan.sample.domain.usecase.GetProductListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun productListUseCaseProvider(repositoryImpl: RepositoryImpl): GetProductListUseCase {
        return GetProductListUseCase(repositoryImpl)
    }

    @Provides
    @Singleton
    fun productDetailUseCaseProvider(repositoryImpl: RepositoryImpl): GetProductDetailUseCase {
        return GetProductDetailUseCase(repositoryImpl)
    }


}