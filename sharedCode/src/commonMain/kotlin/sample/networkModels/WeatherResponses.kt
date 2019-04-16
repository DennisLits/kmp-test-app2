package sample.networkModels

import com.fhyber.multiweather.data.CurrentWeather
import kotlinx.serialization.Serializable
import sample.displayModel.BaseDisplayData
import sample.getCurrentTimeMillis

data class MainDisplayData(
    val fromNetwork : Boolean,
    val city_id: Int,
    val name: String,
    val time: Long,
    val temp: Double,
    val humidity: Int,
    val pressure: Int
)

// Maybe remove, inserting values directly is maybe easier
fun CurrentCityWeatherResponse.toDBModel() : CurrentWeather {
    return CurrentWeather.Impl(
        city_id = id,
        name = name,
        time = getCurrentTimeMillis(),
        temp = main.temp,
        pressure = main.pressure,
        humidity = main.humidity
    )
}
fun CurrentWeather.toDisplayModel(fromNetwork: Boolean) : MainDisplayData {
    return MainDisplayData(
        fromNetwork = fromNetwork,
        city_id = city_id,
        name = name,
        time =  time,
        temp = temp,
        pressure = pressure,
        humidity = humidity
    )
}

@Serializable
data class CurrentCityWeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    var name: String, // for testing, a var
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

@Serializable
data class Main(
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

@Serializable
data class Wind(
    val deg: Int,
    val speed: Double
)

@Serializable
data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

@Serializable
data class Sys(
    val country: String,
    val id: Int,
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

@Serializable
data class Clouds(
    val all: Int
)

@Serializable
data class Coord(
    val lat: Double,
    val lon: Double
)