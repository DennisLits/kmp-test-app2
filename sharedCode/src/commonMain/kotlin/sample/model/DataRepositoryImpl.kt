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




        DBHelper.saveCurrentWeatherForCity(response)



        //val DBData = MainDisplayData.toDB()

        // TODO uncomont
        //data.value = response
    }


    override suspend fun refreshFakeTestModify(cityID: Int) {

        //delay(1000) // DELAY IS BROKEN ON KOTLIN NATIVE ON NON MAIN THREAD?

        DBHelper.saveCurrentWeatherForCityTest(cityID = cityID, name = "Fake Refresh City!")


        /*
        val newData = data.value?.copy()
        newData?.name = "COOL NEW DELAYED PLACE "
        data.value = newData
        */


    }







}