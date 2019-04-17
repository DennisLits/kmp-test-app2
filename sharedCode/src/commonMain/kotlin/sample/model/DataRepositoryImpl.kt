package sample.model

import com.github.florent37.livedata.KLiveData
import com.github.florent37.livedata.KMutableLiveData
import com.squareup.sqldelight.Query
import sample.KApplication
import sample.Log
import sample.api.Error
import sample.api.NetworkApi
import sample.api.WEATHER_HOST
import sample.constants.SettingsKeys
import sample.db.DBHelper
import sample.networkModels.CurrentCityWeatherResponse

import sample.networkModels.MainDisplayData
import sample.networkModels.toDBModel
import sample.networkModels.toDisplayModel
import sample.presentation.DataRepository


class DataRepositoryImpl : DataRepository {


    override val data: KMutableLiveData<MainDisplayData> = KMutableLiveData()
    private val api = NetworkApi(WEATHER_HOST)

    override fun getLData() : KMutableLiveData<MainDisplayData> {
        //data.value?: refresh(cityID)
        //refresh(cityID)
        return data
    }



    override suspend fun searchCity(citySearchText: String) {
        val response = try {
            api.getCurrentWeather(citySearchText)

        } catch (cause: Throwable) {
            Log.e(cause)
            // TODO set error
            throw Error.UpdateProblem()
        }

        // TODO uncomment when settings work again
        response.id = 2643743
        //KApplication.settings[SettingsKeys.LAST_SEARCH] = response.id

        val dbModel = response.toDBModel()
        DBHelper.saveCurrentWeatherForCity(dbModel)

        val disData = dbModel.toDisplayModel(true)
        data.value = disData
    }

    override suspend fun refresh(cityID: Int) {
        val response = try {
            api.getCurrentWeather(cityID)

        } catch (cause: Throwable) {
            Log.e(cause)
            // TODO set error
            throw Error.UpdateProblem()
        }


        response.id = 2643743 // TODO uncomment when settings work again
        val dbModel = response.toDBModel()
        DBHelper.saveCurrentWeatherForCity(dbModel)

        val disData = dbModel.toDisplayModel(true)
        data.value = disData
    }










}