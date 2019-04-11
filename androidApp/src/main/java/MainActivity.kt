package sample


import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.fhyber.multiweather.R
import com.github.florent37.livedata.KLifecycle
import com.github.florent37.livedata.kLifecycle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import sample.model.DataRepositoryImpl
import sample.networkModels.CurrentCityWeatherResponse
import sample.networkModels.MainDisplayData
import sample.presentation.MainPresenter
import sample.presentation.MainView


import timber.log.Timber
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), MainView {
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
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    private val presenter by lazy { MainPresenter(this, DataRepositoryImpl(), lifeCycleOwner = this.kLifecycle()) }



    var isClicked  = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //model = ViewModelProviders.of(this).get(TestViewModel::class.java)

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

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }


}