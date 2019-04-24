package sample.screens

import android.view.View
import android.widget.Toast
import com.fhyber.multiweather.R
import com.github.florent37.livedata.kLifecycle
import kotlinx.android.synthetic.main.weather_search_screen.*
import sample.Log
import sample.activities.BaseActivity
import sample.hide
import sample.model.DataRepositoryImpl
import sample.networkModels.MainDisplayData
import sample.presentation.WeatherPresenter
import sample.presentation.MainView
import sample.show

class WeatherSearchScreen(parentAct : BaseActivity) : BaseScreen(parentAct = parentAct), MainView {


    override val presenter by lazy { WeatherPresenter(this, DataRepositoryImpl(), lifeCycleOwner = getAct().kLifecycle()) }



    init {
        // Always should be first line of init
        view = getAct().layoutInflater.inflate(R.layout.weather_search_screen, parentAct.rootView, false)


        fabGo.setOnClickListener {

            val searchString = etUserName.text.toString()
            presenter.loadData(searchString)



        }
    }

    override fun displayData(data: MainDisplayData) {



        tvName.visibility = View.VISIBLE
        Log.i("Displaying data of ${data.name}")
        with(data) {
            tvName.text = name + (if (fromNetwork) " from network" else " from DB")
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
        tvName.hide()
    }

    override fun hideLoader() {
        pb.hide()
        tvName.show()
    }


    override fun showError(error: String) {
        error.let {
            Toast.makeText(getAct(), error, Toast.LENGTH_LONG).show()
        }
    }


}