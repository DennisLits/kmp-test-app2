package sample

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * Created by @iamBedant on 13/11/18.
 */
actual object Log{
    actual fun d(message: String) {
        Log.d(message)
    }

    actual fun e(message: String) {
        Log.e(message)
    }

    actual fun i(message: String) {
        Log.i(message)
    }

    actual fun e(error: Throwable) {
        Log.e(error)
    }
}

actual fun getMainDispetcher(): CoroutineDispatcher {
    return Dispatchers.Main
}

actual fun <T> runTest(block: suspend () -> T) {
    runBlocking { block() }
}