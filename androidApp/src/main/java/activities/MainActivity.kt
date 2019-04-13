package sample.activities

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.fhyber.multiweather.R
import kotlinx.android.synthetic.main.activity_main.*
import sample.activities.BaseActivity
import sample.screens.BaseScreen
import sample.screens.WeatherSearchScreen

class MainActivity : BaseActivity() {

    override val rootView: ViewGroup
        get() = root // The synthetic view


    lateinit var currentScreen : BaseScreen


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO proper navigation
        currentScreen = WeatherSearchScreen(this)
        root.addView(currentScreen.view)



    }

    override fun onStart() {
        super.onStart()
        currentScreen.onStart()
    }

    override fun onStop() {
        super.onStop()
        currentScreen.onStop()
    }


}