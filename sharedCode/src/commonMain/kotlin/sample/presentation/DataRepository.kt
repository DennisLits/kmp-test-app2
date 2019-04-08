package sample.presentation

import com.github.florent37.livedata.KLiveData
import com.github.florent37.livedata.KMutableLiveData
import sample.AllData
import sample.networkModels.CurrentCityWeatherResponse

interface DataRepository{
    val data : KMutableLiveData<CurrentCityWeatherResponse>
    fun getData() : KLiveData<CurrentCityWeatherResponse>
    suspend fun refresh(cityID: String)
    suspend fun refreshFakeTestModify(cityID: String)
}