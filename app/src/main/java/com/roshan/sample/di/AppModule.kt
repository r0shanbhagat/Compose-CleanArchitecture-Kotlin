package com.roshan.sample.di


import com.roshan.sample.data.respository.RepositoryImpl
import com.roshan.sample.domain.repository.Repository
import com.roshan.sample.domain.usecase.GetProductDetailUseCase
import com.roshan.sample.domain.usecase.GetProductListUseCase
import com.roshan.sample.presentation.viewmodel.ProductDetailVewModel
import com.roshan.sample.presentation.viewmodel.ProductListVewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewmodelModule = module {
    viewModelOf(::ProductDetailVewModel)
    viewModelOf(::ProductListVewModel)
}

val repositoryModule = module {
    single<Repository> { RepositoryImpl(get()) }
}

val useCaseModule = module {
    singleOf(::GetProductListUseCase)
    singleOf(::GetProductDetailUseCase)
}

val appModule = listOf(
    viewmodelModule,
    repositoryModule,
    useCaseModule,
    dispatcherModule,
    networkModule
)