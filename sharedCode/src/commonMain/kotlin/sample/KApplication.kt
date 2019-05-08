package sample

import com.fhyber.multiweather.WeatherDb
import com.russhwolf.settings.Settings

import com.squareup.sqldelight.db.SqlDriver


// on IOS we get frozen/immutable problems if these variables are inside of an object, as top level they work fine
lateinit var settings: Settings
lateinit var database : WeatherDb

// Used to init App Wide things, has Singletons
object KApplication {



    fun setupApp() {
        database = WeatherDb(getDatabaseDriver())
        settings = getSettingsFactory().create("test_settings")
    }





    /*
    val database = Database(driver)
    val playerQueries: PlayerQueries = database.playerQueries
     */

}