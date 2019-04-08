package sample

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sample.api.Error
import kotlin.coroutines.CoroutineContext


fun launchAndCatch(
    context: CoroutineContext,
    onError: (String) -> Unit,
    function: suspend () -> Unit
): Finalizable {
    val ret = Finalizable()
    GlobalScope.launch(context) {
        try {
            function()
        } catch (e: Throwable) {
            onError(e.message?: "there was an error GENERIC REMOVE TODO")
        } finally {
            ret.onFinally?.invoke()
        }
    }

    return ret
}

class Finalizable {
    var onFinally: (() -> Unit)? = null

    infix fun finally(f: () -> Unit) {
        onFinally = f
    }
}