package sample.networkModels

import kotlinx.serialization.Serializable


data class ForecastDisplayData(
    val fromNetwork : Boolean,
    val city: CurrentCityForecastResponse
)

fun CurrentCityForecastResponse.toDisplayModel(fromNetwork: Boolean) : ForecastDisplayData {
    return ForecastDisplayData(
        fromNetwork = fromNetwork,
        city = this
    )
}

@Serializable
data class CurrentCityForecastResponse(
    val cnt: Int,
    val id: Int? = null,
    val list: List<WeatherDay>
)

@Serializable
data class WeatherDay(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val rain : Precipitation? = null,
    val snow : Precipitation? = null
)


