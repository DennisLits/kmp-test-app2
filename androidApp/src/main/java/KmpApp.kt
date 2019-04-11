package sample

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fhyber.multiweather.BuildConfig
import com.fhyber.multiweather.WeatherDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import timber.log.Timber
import timber.log.Timber.plant
import timber.log.Timber.DebugTree



/**
 * Created by @iamBedant on 13/11/18.
 */

class KmpApp : Application() {

    private val app = KApplication()

   override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            //Timber.plant(CrashReportingTree())
        }

       // !IMPORTANT!
       val driver: SqlDriver = AndroidSqliteDriver(WeatherDb.Schema, this, "test.db")
       app.initDatabase(driver)




    }



}