package sample

import com.fhyber.multiweather.WeatherDb
import com.russhwolf.settings.PlatformSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver

// Create once app wide on app startup, inits classes that need to exist for other features to work in AppDelegate maybe?
class CommonIOSApp {

    private val app = KApplication()

    init {

        val driver: SqlDriver = NativeSqliteDriver(WeatherDb.Schema, "test.db")
        val settingsFactory : Settings.Factory = PlatformSettings.Factory()

        app.init(driver, settingsFactory)

    }



}