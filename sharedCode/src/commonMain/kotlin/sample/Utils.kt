package sample

import com.github.florent37.livedata.KLiveData
import kotlinx.coroutines.CoroutineDispatcher


expect object Log{
    fun d(message: String)
    fun e(error:Throwable)
    fun e(message: String)
    fun i(message: String)
}

expect fun getMainDispetcher(): CoroutineDispatcher

expect fun <T> runTest(block: suspend () -> T)



fun boo() {
    val dude : KLiveData<Boolean>
}