package sample

import com.fhyber.multiweather.BuildConfig
import timber.log.Timber


class KmpApp : CommonApplication() {



   override fun onCreate() {
       super.onCreate()

       // probably should be in CommonApplication
       if (BuildConfig.DEBUG) {
           Timber.plant(Timber.DebugTree())
       } else {
           //Timber.plant(CrashReportingTree())
       }




   }



}