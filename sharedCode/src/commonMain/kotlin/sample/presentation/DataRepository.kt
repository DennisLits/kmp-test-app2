package sample.presentation

import com.github.florent37.livedata.KMutableLiveData
import sample.networkModels.CurrentWeatherDisplayData
import sample.networkModels.ForecastDisplayData

interface DataRepository{
    val currentData : KMutableLiveData<CurrentWeatherDisplayData>
    val forecastData : KMutableLiveData<ForecastDisplayData>

    fun getLiveCurrentData() : KMutableLiveData<CurrentWeatherDisplayData>
    fun getLiveForecastData() : KMutableLiveData<ForecastDisplayData>

    suspend fun getCityCurrentWeatherBySearch(citySearchText: String)
    suspend fun getCityCurrentWeatherByID(cityID: Int)
    suspend fun getCityForecastBySearch(citySearchText: String)
    suspend fun getCityForecastByID(cityID: Int)
}