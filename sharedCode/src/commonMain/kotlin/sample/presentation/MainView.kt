package sample.presentation
import com.github.florent37.livedata.KLifecycle
import sample.DisplayData
import sample.networkModels.CurrentCityWeatherResponse


interface MainView : BaseView {
    fun displayData(data: CurrentCityWeatherResponse) // for now
    fun showLoader()
    fun hideLoader()
}