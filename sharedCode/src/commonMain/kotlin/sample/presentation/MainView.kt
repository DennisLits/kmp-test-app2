package sample.presentation
import sample.DisplayData
import sample.networkModels.CurrentCityWeatherResponse


interface MainView : BaseView{
    fun displayData(data: CurrentCityWeatherResponse) // for now
    fun showLoader()
    fun hideLoader()
}