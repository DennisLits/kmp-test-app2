package sample


import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineDispatcher

// Figure out better way to determine if debug or not
fun isDebug() : Boolean {
    return true
}

expect object Log{
    fun d(message: String)
    fun e(error:Throwable)
    fun e(message: String)
    fun i(message: String)
}

expect fun getMainDispatcher(): CoroutineDispatcher

expect fun <T> runTest(block: suspend () -> T)

expect fun isAndroid(): Boolean

expect fun getLString(stringKey: String) : String

expect fun getCurrentTimeMillis() : Long

expect fun getDatabaseDriver() : SqlDriver

expect fun getSettingsFactory() : Settings.Factory
