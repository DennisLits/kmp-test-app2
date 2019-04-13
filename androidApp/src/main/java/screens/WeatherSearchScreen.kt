package sample.screens

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fhyber.multiweather.R
import com.github.florent37.livedata.kLifecycle
import kotlinx.android.synthetic.main.weather_search_screen.*
import sample.Log
import sample.activities.BaseActivity
import sample.hide
import sample.model.DataRepositoryImpl
import sample.networkModels.MainDisplayData
import sample.presentation.MainPresenter
import sample.presentation.MainView
import sample.show

class WeatherSearchScreen(parentAct : BaseActivity) : BaseScreen(parentAct = parentAct), MainView {


    override val presenter by lazy { MainPresenter(this, DataRepositoryImpl(), lifeCycleOwner = getAct().kLifecycle()) }
    var isClicked  = false

    init {
        // Always should be first line of init
        view = getAct().layoutInflater.inflate(R.layout.weather_search_screen, parentAct.rootView, false)


        fabGo.setOnClickListener {
            //presenter.loadData(etUserName.text.toString())
            if(!isClicked) {
                presenter.loadData(2172797)
                isClicked = true
            }
            else {
                presenter.modifyDataForT()

            }

        }
    }

    override fun displayData(data: MainDisplayData) {



        tvName.visibility = View.VISIBLE
        Log.i("Displaying data of ${data.name}")
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


    override fun showError(error: String) {
        error.let {
            Toast.makeText(getAct(), error, Toast.LENGTH_LONG).show()
        }
    }


}