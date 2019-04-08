package sample


import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fhyber.multiweather.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers

import sample.model.DataRepositoryImpl
import sample.networkModels.CurrentCityWeatherResponse
import sample.presentation.MainPresenter
import sample.presentation.MainView
import timber.log.Timber
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), MainView {

    override fun showLoader() {
        pb.show()
        tvBio.hide()
        tvName.hide()
        tvGists.hide()
        tvRepos.hide()
        ivAvatar.hide()
    }

    override fun hideLoader() {
        pb.hide()
        tvBio.show()
        tvName.show()
        tvGists.show()
        tvRepos.show()
        ivAvatar.show()
    }

    override fun displayData(data: CurrentCityWeatherResponse) {
        with(data) {
            tvName.text = name
            /*
            Glide.with(this@MainActivity).load(avatarUrl).into(ivAvatar)
            tvRepos.text = publicRepos
            tvGists.text = publicGists
            tvBio.text = bio
            */
        }
    }

    override fun showError(error: String) {
        error.let {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    private val presenter by lazy { MainPresenter(this, DataRepositoryImpl()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabGo.setOnClickListener {
            //presenter.loadData(etUserName.text.toString())
            presenter.loadData("2172797")
        }
    }
}