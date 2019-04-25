package sample.model

import com.github.florent37.livedata.KMutableLiveData
import sample.Log
import sample.api.Error
import sample.api.NetworkApi
import sample.api.WEATHER_HOST
import sample.db.DBHelper
import sample.networkModels.*

import sample.presentation.DataRepository
import sample.utils.SettingsUtils


class DataRepositoryImpl : DataRepository {



    override val currentData: KMutableLiveData<CurrentWeatherDisplayData> = KMutableLiveData()
    override val forecastData: KMutableLiveData<ForecastDisplayData> = KMutableLiveData()

    private val api = NetworkApi(WEATHER_HOST)

    override fun getLiveCurrentData() : KMutableLiveData<CurrentWeatherDisplayData> {
        //data.value?: refresh(cityID)
        //refresh(cityID)
        return currentData
    }
    override fun getLiveForecastData() : KMutableLiveData<ForecastDisplayData> {
        return forecastData
    }



    override suspend fun getCityCurrentWeatherBySearch(citySearchText: String) {
        val response = try {
            api.getCurrentWeather(citySearchText)
        } catch (cause: Throwable) {
            Log.e(cause)
            // TODO set error
            throw Error.UpdateProblem()
        }

        saveAndDisplayCurrentWeather(response)

        val response2 = try {
            api.getCurrentForecast(citySearchText)
        } catch (cause: Throwable) {
            Log.e(cause)
            throw Error.UpdateProblem()
        }
        forecastData.value = response2.toDisplayModel(true)


    }

    override suspend fun getCityCurrentWeatherByID(cityID: Int) {
        val response = try {
            api.getCurrentWeather(cityID)
        } catch (cause: Throwable) {
            Log.e(cause)
            // TODO set error
            throw Error.UpdateProblem()
        }

        saveAndDisplayCurrentWeather(response)

        val response2 = try {
            api.getCurrentForecast(cityID)
        } catch (cause: Throwable) {
            Log.e(cause)
            throw Error.UpdateProblem()
        }
        forecastData.value = response2.toDisplayModel(true)
    }

    private fun saveAndDisplayCurrentWeather(response: CurrentCityWeatherResponse) {
        SettingsUtils.setLastUserSearchedCityID(response.id)
        val dbModel = response.toDBModel()
        DBHelper.saveCurrentWeatherForCity(dbModel)

        val disData = dbModel.toDisplayModel(true)
        currentData.value = disData
    }

    override suspend fun getCityForecastBySearch(citySearchText: String) {



    }

    override suspend fun getCityForecastByID(cityID: Int) {



    }










}