package sample.model

import com.github.florent37.livedata.KLiveData
import com.github.florent37.livedata.KMutableLiveData
import com.squareup.sqldelight.Query
import sample.KApplication
import sample.Log
import sample.api.Error
import sample.api.NetworkApi
import sample.db.DBHelper
import sample.networkModels.CurrentCityWeatherResponse

import sample.networkModels.MainDisplayData
import sample.networkModels.toDBModel
import sample.networkModels.toDisplayModel
import sample.presentation.DataRepository


class DataRepositoryImpl : DataRepository {


    override val data: KMutableLiveData<MainDisplayData> = KMutableLiveData()
    private val api = NetworkApi("https://github.com")

    override fun getLData() : KMutableLiveData<MainDisplayData> {
        //data.value?: refresh(cityID)
        //refresh(cityID)
        return data
    }

    override suspend fun refresh(cityID: Int) {
        val response = try {
            api.getCurrentWeather(cityID)

        } catch (cause: Throwable) {
            Log.e(cause)
            // TODO set error
            throw Error.UpdateProblem()
        }



        // Uncomment to actually write to DB

        DBHelper.saveCurrentWeatherForCity(response)

        val disData = response.toDBModel().toDisplayModel()


        // uncomont if using livedatas
        data.value = disData

    }


    override suspend fun refreshFakeTestModify(cityID: Int) {

        //delay(1000) // DELAY IS BROKEN ON KOTLIN NATIVE ON NON MAIN THREAD?

        val disData = MainDisplayData(name = "Fake Refresh City!")
        DBHelper.saveCurrentWeatherForCityTest(cityID = cityID, name = disData.name)
        data.value = disData


        /*
        val newData = data.value?.copy()
        newData?.name = "COOL NEW DELAYED PLACE "
        data.value = newData
        */


    }







}