package sample

import com.fhyber.multiweather.WeatherDb
import com.russhwolf.settings.PlatformSettings
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.ios.NativeSqliteDriver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.runBlocking
import platform.Foundation.NSRunLoop
import platform.Foundation.performBlock
import kotlin.coroutines.CoroutineContext

actual object Log {
    actual fun d(message: String) {
        print(message + "\n")
    }
    actual fun e(message: String) {
        print(message + "\n")
    }

    actual fun i(message: String) {
        print(message + "\n")
    }

    actual fun e(error: Throwable) {
        print(error)
    }
}

object MainLoopDispatcher: CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        NSRunLoop.mainRunLoop().performBlock {
            block.run()
        }
    }
}

actual fun getMainDispatcher(): CoroutineDispatcher {
    return MainLoopDispatcher
}

actual fun <T> runTest(block: suspend () -> T) {
    runBlocking { block() }
}

actual fun isAndroid(): Boolean = false



actual fun getLString(stringKey: String): String {
    // TODO implement real lookup from inside CommonIOSApp class
    return stringKey
}