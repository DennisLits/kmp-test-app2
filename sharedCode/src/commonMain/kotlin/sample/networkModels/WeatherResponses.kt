package sample.networkModels

import com.fhyber.multiweather.data.CurrentWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sample.getCurrentTimeMillis

data class CurrentWeatherDisplayData(
    val fromNetwork : Boolean,
    val city_id: Int,
    val name: String,
    val time: Long,
    val temp: Double,
    val humidity: Int,
    val pressure: Double
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
fun CurrentWeather.toDisplayModel(fromNetwork: Boolean) : CurrentWeatherDisplayData {
    return CurrentWeatherDisplayData(
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
    val cod: Int? = null,
    val coord: Coord? = null,
    val dt: Int,
    var id: Int, // Var for testing
    val main: Main,
    val name: String,
    val sys: Sys? = null,
    val visibility: Int? = null,
    val weather: List<Weather>,
    val wind: Wind,
    val rain : Precipitation? = null,
    val snow : Precipitation? = null
)


@Serializable
data class Main(
    val humidity: Int,
    val pressure: Double,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

@Serializable
data class Wind(
    val deg: Double,
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
    val country: String? = null,
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

@Serializable
data class Precipitation (
    @SerialName("3h")
    val threeHours: Double? = null,
    @SerialName("5h")
    val fiveHours: Double? = null
)