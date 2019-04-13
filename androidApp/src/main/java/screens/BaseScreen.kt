package sample.screens

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.extensions.LayoutContainer
import sample.activities.BaseActivity
import sample.presentation.BasePresenter

abstract class BaseScreen(
    val parentAct : BaseActivity
    ) : LayoutContainer {

    abstract val presenter : BasePresenter
    lateinit var view : View

    // Maybe just use this one, it's required for LayoutContainer
    override val containerView: View?
        get() = view


    fun getAct() : BaseActivity = parentAct
    fun onStart(){
        presenter.onStart()
    }
    fun onStop(){
        presenter.onStop()
    }

}