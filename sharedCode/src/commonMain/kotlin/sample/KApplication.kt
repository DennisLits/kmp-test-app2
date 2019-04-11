package sample

import com.fhyber.multiweather.WeatherDb
import com.squareup.sqldelight.db.SqlDriver

lateinit var database : WeatherDb

class KApplication {


    fun initDatabase(driver : SqlDriver) {
        database = WeatherDb(driver)
    }

    // TODO convert to DI or service locater
    companion object {
        fun getDB() = database
    }


    /*
    val database = Database(driver)
    val playerQueries: PlayerQueries = database.playerQueries

     */

}