package com.roshan.sample.di

import com.roshan.sample.di.DispatcherDI.DISPATCHER_DEFAULT
import com.roshan.sample.di.DispatcherDI.DISPATCHER_IO
import com.roshan.sample.di.DispatcherDI.DISPATCHER_MAIN
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * @Details DispatcherModule
 * @Author Roshan Bhagat
 */

object DispatcherDI {
    const val DISPATCHER_IO = "IODispatcher"
    const val DISPATCHER_DEFAULT = "DefaultDispatcher"
    const val DISPATCHER_MAIN = "MainDispatcher"
}

val dispatcherKoinModule = module {
    single(named(DISPATCHER_IO)) { Dispatchers.IO }
    single(named(DISPATCHER_DEFAULT)) { Dispatchers.Default }
    single(named(DISPATCHER_MAIN)) { Dispatchers.Main }
}


