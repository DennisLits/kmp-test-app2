package sample.networkModels

import com.fhyber.multiweather.data.CurrentWeather
import kotlinx.serialization.Serializable
import sample.displayModel.BaseDisplayData

data class MainDisplayData (
    val name: String
)

// Maybe remove, inserting values directly is maybe easier
fun CurrentCityWeatherResponse.toDBModel() : CurrentWeather {
    return CurrentWeather.Impl(
        city_id = this.id,
        name = this.name,
        time = 1
    )
}
fun CurrentWeather.toDisplayModel() : MainDisplayData {
    return MainDisplayData(
        name = this.name
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