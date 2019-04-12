package sample

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

