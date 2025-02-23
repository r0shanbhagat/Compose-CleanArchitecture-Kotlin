package com.roshan.sample.di

import com.roshan.sample.domain.usecase.GetProductDetailUseCase
import com.roshan.sample.domain.usecase.GetProductListUseCase
import org.koin.dsl.module

/**
 * All use case dependency can be prepared here.
 * For demonstration created Landing Use case alone.
 */
val useCaseKoinModule = module {
    factory<GetProductDetailUseCase> {
        GetProductDetailUseCase(get())
    }
    factory<GetProductListUseCase> {
        GetProductListUseCase(get())
    }
}