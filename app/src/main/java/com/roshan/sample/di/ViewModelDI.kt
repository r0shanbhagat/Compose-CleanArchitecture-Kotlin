package com.roshan.sample.di


import com.roshan.sample.presentation.viewmodel.ProductDetailVewModel
import com.roshan.sample.presentation.viewmodel.ProductListVewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


/**
 * @Details View Model Dependency Injection stored herel
 * @Author Roshan Bhagat
 * @constructor Create Repository module
 */
val viewmodelKoinModule = module {
    viewModel { ProductDetailVewModel(get()) }
    viewModel { ProductListVewModel(get()) }
}
