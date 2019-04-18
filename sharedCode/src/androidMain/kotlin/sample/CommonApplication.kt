package sample

import android.app.Application
import com.fhyber.common_android_sub_module.BuildConfig
import com.fhyber.multiweather.WeatherDb

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import timber.log.Timber

open class CommonApplication : Application() {

    companion object {
        lateinit var instance : CommonApplication
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

        // probably should be in CommonApplication
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }


        // Important for DB/Settings
        KApplication.setupApp()

        //val settingsFactory : Settings.Factory = PlatformSettings.Factory(this)


    }


}