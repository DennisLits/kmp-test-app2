package sample

import kotlinx.coroutines.CoroutineDispatcher


expect object Log{
    fun d(message: String)
    fun e(error:Throwable)
    fun e(message: String)
    fun i(message: String)
}

expect fun getMainDispatcher(): CoroutineDispatcher

expect fun <T> runTest(block: suspend () -> T)

expect fun isAndroid(): Boolean