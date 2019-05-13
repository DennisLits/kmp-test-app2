package sample.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fhyber.multiweather.R
import com.github.florent37.livedata.kLifecycle
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_forecast.*
import kotlinx.android.synthetic.main.weather_search_screen.*
import sample.Log
import sample.activities.BaseActivity
import sample.hide
import sample.model.DataRepositoryImpl
import sample.networkModels.CurrentWeatherDisplayData
import sample.networkModels.ForecastDisplayData
import sample.networkModels.WeatherDay
import sample.presentation.WeatherPresenter
import sample.presentation.MainView
import sample.show
import java.util.*
import kotlin.collections.ArrayList

class WeatherSearchScreen(parentAct : BaseActivity) : BaseScreen(parentAct = parentAct), MainView {


    override val presenter by lazy { WeatherPresenter(this, DataRepositoryImpl(), lifeCycleOwner = getAct().kLifecycle()) }

    private val forecastAdapter : ForecastAdapter

    init {
        // Always should be first line of init
        view = getAct().layoutInflater.inflate(R.layout.weather_search_screen, parentAct.rootView, false)

        forecastAdapter = ForecastAdapter()
        setupForecastList()

        fabGo.setOnClickListener {
            val searchString = etUserName.text.toString()
            presenter.loadData(searchString)
        }

    }

    private fun setupForecastList() {
        rvForecast.layoutManager = LinearLayoutManager(getAct(), RecyclerView.HORIZONTAL, false)
        rvForecast.adapter = forecastAdapter
    }

    class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastHolder>() {

        var content : List<WeatherDay> = ArrayList(0)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_forecast, parent, false)

            return ForecastHolder(view)
        }

        override fun getItemCount(): Int {
            return content.size
        }

        override fun onBindViewHolder(holder: ForecastHolder, position: Int) {

            val weatherDay = content[position]
            holder.tvTemp.text = weatherDay.main.temp.toString()

            val cal = Calendar.getInstance()
            cal.timeInMillis = weatherDay.dt.toLong() * 1000L

            holder.tvDate.text = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

        }

        fun setNewData(newContent: List<WeatherDay>) {
            content = newContent
            notifyDataSetChanged()
        }

        class ForecastHolder(itemView: View) : ViewHolderContainer(itemView) {

        }
        abstract class ViewHolderContainer(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
            override val containerView: View?
                get() = itemView
        }

    }

    override fun displayCurrentData(data: CurrentWeatherDisplayData) {

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

    override fun displayForecastData(data: ForecastDisplayData) {
        Log.i("Displaying FORECAST data")
        forecastAdapter.setNewData(data.list)

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