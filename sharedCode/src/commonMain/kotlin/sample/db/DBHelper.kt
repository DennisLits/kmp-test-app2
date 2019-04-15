package sample.db

import com.fhyber.multiweather.data.CurrentWeather
import com.squareup.sqldelight.Query
import sample.KApplication
import sample.networkModels.CurrentCityWeatherResponse


// We can either use DB directly or convert to DB model and insert that
// Not sure what is easier in the long run maybe not much difference

class DBHelper{
    companion object {

        fun saveCurrentWeatherForCity(response: CurrentCityWeatherResponse) {
            KApplication.database.weatherQueries.insertCity(
                city_id = response.id,
                name = response.name + " FROM DB NAME 1",
                time = 1
            )
        }
        fun saveCurrentWeatherForCityTest(cityID: Int, name: String) {
            KApplication.database.weatherQueries.insertCity(
                city_id = cityID,
                name = name + " FROM DB NAME 2",
                time = 1
            )
        }

        fun getCurrentWeatherForCity(cityID: Int): Query<CurrentWeather> {
            return KApplication.database.weatherQueries.selectCity(cityID)
        }

    }
}


