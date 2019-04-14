package sample

import android.app.Application
import com.fhyber.multiweather.WeatherDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

open class CommonApplication : Application() {

    companion object {
        lateinit var instance : CommonApplication
    }

    private val app = KApplication()

    override fun onCreate() {
        super.onCreate()
        instance = this


        val driver: SqlDriver = AndroidSqliteDriver(WeatherDb.Schema, this, "test.db")
        app.initDatabase(driver)

    }


}