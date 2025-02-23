package com.roshan.sample

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import com.roshan.sample.di.appModule
import com.roshan.sample.utils.AppSession
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @Details PlaygroundApp is the Application class.
 * @Author Roshan Bhagat
 */

class MainApplication : Application(), ImageLoaderFactory {

    internal val sessionData: AppSession by inject()

    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainApplication)
            androidLogger(Level.DEBUG)
            modules(appModule)
        }
    }

    /**
     * Since we're displaying SVGs in the app, Coil needs an ImageLoader which supports this
     * format. During Coil's initialization it will call `applicationContext.newImageLoader()` to
     * obtain an ImageLoader.
     *
     * @see https://github.com/coil-kt/coil/blob/main/coil-singleton/src/main/java/coil/Coil.kt#L63
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this).components {
            add(SvgDecoder.Factory())
        }.build()
    }

    override fun onTerminate() {
        super.onTerminate()
        sessionData.clear()
    }


    companion object {
        lateinit var instance: MainApplication
    }
}