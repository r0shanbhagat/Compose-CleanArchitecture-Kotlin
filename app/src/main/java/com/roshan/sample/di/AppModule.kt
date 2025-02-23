package com.roshan.sample.di

/**
 * @Details :AppModule
 * @Author Roshan Bhagat
 */
import com.roshan.sample.utils.AppSession
import org.koin.dsl.module

// Main Koin Module
val appModule = module {

    single { AppSession() }
    includes(
        viewmodelKoinModule,
        useCaseKoinModule,
        movieRepositoryKoinModule,
        movieDsKoinModule,
        networkKoinModule,
        dispatcherKoinModule,
    )
}