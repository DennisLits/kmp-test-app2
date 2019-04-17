package sample.db

import com.fhyber.multiweather.data.CurrentWeather

import com.squareup.sqldelight.Query
import sample.KApplication
import sample.database
import sample.getCurrentTimeMillis
import sample.networkModels.CurrentCityWeatherResponse


// We can either use DB directly or convert to DB model and insert that
// Not sure what is easier in the long run maybe not much difference

class DBHelper{
    companion object {

        fun saveCurrentWeatherForCity(response: CurrentWeather) {
            database.weatherQueries.insertCityModel(response)
        }




        fun getCurrentWeatherForCity(cityID: Int): Query<CurrentWeather> {
            return database.weatherQueries.selectCity(cityID)
        }

    }
}


