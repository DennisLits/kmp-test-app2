package sample

import com.fhyber.multiweather.WeatherDb
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver


// Used to init App Wide things, has Singletons
class KApplication {




    fun init(driver : SqlDriver, settingsFactory: Settings.Factory) {
        database = WeatherDb(driver)
        settings = settingsFactory.create("test_settings")

    }

    // TODO convert to DI or service locater
    companion object {
        // TODO check if settings maybe needs to be ThreadLocal? Probably would break it if it was
        lateinit var settings: Settings
        lateinit var database : WeatherDb

    }


    /*
    val database = Database(driver)
    val playerQueries: PlayerQueries = database.playerQueries

     */

}