package sample

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fhyber.multiweather.BuildConfig
import timber.log.Timber
import timber.log.Timber.plant
import timber.log.Timber.DebugTree



/**
 * Created by @iamBedant on 13/11/18.
 */

class KmpApp : Application() {

   override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            //Timber.plant(CrashReportingTree())

        }



    }



}