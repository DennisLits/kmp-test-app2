package sample.presentation

import com.github.florent37.livedata.KLiveData
import com.github.florent37.livedata.KMutableLiveData
import sample.networkModels.CurrentCityWeatherResponse
import sample.networkModels.MainDisplayData

interface DataRepository{
    val data : KMutableLiveData<MainDisplayData>
    fun getLData() : KMutableLiveData<MainDisplayData>
    suspend fun refresh(cityID: Int)
    suspend fun refreshFakeTestModify(cityID: Int)
}