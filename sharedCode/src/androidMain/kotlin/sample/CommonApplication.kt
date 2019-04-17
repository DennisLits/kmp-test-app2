package sample

import android.app.Application
import com.fhyber.multiweather.WeatherDb

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

open class CommonApplication : Application() {

    companion object {
        lateinit var instance : CommonApplication
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

        // Important for DB/Settings
        KApplication.init()

        //val settingsFactory : Settings.Factory = PlatformSettings.Factory(this)


    }


}