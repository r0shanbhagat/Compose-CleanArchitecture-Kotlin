package com.roshan.sample.di

import com.roshan.sample.data.respository.RepositoryImpl
import com.roshan.sample.domain.repository.Repository
import org.koin.dsl.module

/**
 * @Details :Repository DI
 * @Author Roshan Bhagat
 */

val movieRepositoryKoinModule = module {
    single<Repository> {
        RepositoryImpl(get())
    }
}

//Data SourceModule
val movieDsKoinModule = module {
    // factory<DataSourc> { RemoteDataSource(get()) }
}