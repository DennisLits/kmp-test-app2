package sample.presentation

import com.github.florent37.livedata.KLifecycle
import sample.isAndroid

open class BasePresenter(private val lifeCycleOwner: KLifecycle) {

    // Android life cycles handle their own life
    open fun onStart(){
        if (!isAndroid()) {
            lifeCycleOwner.start()
        }
    }
    open fun onStop(){
        if (!isAndroid()) {
            lifeCycleOwner.start()
        }
    }

}