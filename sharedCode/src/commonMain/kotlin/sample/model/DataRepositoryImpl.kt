package sample.model

import com.github.florent37.livedata.KLiveData
import com.github.florent37.livedata.KMutableLiveData
import kotlinx.coroutines.delay
import sample.AllData
import sample.Log
import sample.api.Error
import sample.api.NetworkApi
import sample.networkModels.CurrentCityWeatherResponse
import sample.presentation.DataRepository


class DataRepositoryImpl : DataRepository {


    override val data: KMutableLiveData<CurrentCityWeatherResponse> = KMutableLiveData()
    private val api = NetworkApi("https://github.com")

    override fun getData() : KLiveData<CurrentCityWeatherResponse> {
        //data.value?: refresh(cityID)
        //refresh(cityID)
        return data
    }

    override suspend fun refresh(cityID: String) {
        val response = try {
            api.getCurrentWeather(cityID)

        } catch (cause: Throwable) {
            Log.e(cause)
            // TODO set error
            throw Error.UpdateProblem()
        }

        data.value = response
    }


    override suspend fun refreshFakeTestModify(cityID: String) {

        //delay(1000) // DELAY IS BROKEN ON KOTLIN NATIVE ON NON MAIN THREAD?

        val newData = data.value?.copy()
        newData?.name = "COOL NEW DELAYED PLACE "


        data.value = newData


    }







}