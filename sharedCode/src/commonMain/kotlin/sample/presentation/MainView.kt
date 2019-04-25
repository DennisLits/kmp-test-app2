package sample.presentation
import sample.networkModels.CurrentWeatherDisplayData
import sample.networkModels.ForecastDisplayData


interface MainView : BaseView {
    fun displayCurrentData(data: CurrentWeatherDisplayData)
    fun displayForecastData(data: ForecastDisplayData)
    fun showLoader()
    fun hideLoader()
}