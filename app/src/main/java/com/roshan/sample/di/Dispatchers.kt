package com.roshan.sample.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * @Details DispatcherModule
 * @Author Roshan Bhagat
 * *
 * @constructor Create Dispatcher module
 */
object DispatcherModule {
    const val DEFAULT_DISPATCHER = "DefaultDispatcher"
    const val IO_DISPATCHER = "IODispatcher"
    const val MAIN_DISPATCHER = "MainDispatcher"
}

val dispatcherModule = module {
    single(named(DispatcherModule.DEFAULT_DISPATCHER)) { Dispatchers.Default }
    single(named(DispatcherModule.IO_DISPATCHER)) { Dispatchers.IO }
    single(named(DispatcherModule.MAIN_DISPATCHER)) { Dispatchers.Main }
}