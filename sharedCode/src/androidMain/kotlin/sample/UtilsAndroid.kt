package sample

import com.fhyber.multiweather.WeatherDb
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import timber.log.Timber

actual object Log{
    actual fun d(message: String) {
        Timber.d(message)
    }

    actual fun e(message: String) {
        Timber.e(message)
    }

    actual fun i(message: String) {
        Timber.i(message)
    }

    actual fun e(error: Throwable) {
        Timber.e(error)
    }
}

actual fun isAndroid(): Boolean = true

actual fun getMainDispatcher(): CoroutineDispatcher {
    return Dispatchers.Main
}

actual fun <T> runTest(block: suspend () -> T) {
    runBlocking { block() }
}

private const val STRING_TYPE_RES = "string"
private const val PACKAGE_NAME = "com.fhyber.multiweather" // KEEP UPDATED
actual fun getLString(stringKey: String): String {
    return CommonApplication.instance.getString(
        CommonApplication.instance.resources.getIdentifier(stringKey, STRING_TYPE_RES, PACKAGE_NAME)
    )
}

actual fun getCurrentTimeMillis(): Long {
    return System.currentTimeMillis()
}

actual fun getDatabaseDriver(): SqlDriver {
    return AndroidSqliteDriver(WeatherDb.Schema, CommonApplication.instance, "test.db")
}

actual fun getSettingsFactory(): Settings.Factory {
    return AndroidSettings.Factory(CommonApplication.instance)
}