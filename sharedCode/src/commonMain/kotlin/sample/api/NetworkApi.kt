package sample.api

import io.ktor.client.HttpClient

import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.JsonSerializer
import io.ktor.client.features.json.serializer.KotlinxSerializer

import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.*
import io.ktor.util.StringValues
import kotlinx.serialization.Serializer
import kotlinx.serialization.json.Json
import sample.Log
import sample.isDebug
import sample.networkModels.CurrentCityForecastResponse
import sample.networkModels.CurrentCityWeatherResponse
import kotlin.native.concurrent.ThreadLocal


const val WEATHER_HOST = "api.openweathermap.org"

const val METRIC_UNITS = "metric"
const val WEATHER_API_KEY = "88feca02d25f9d3fde957167bb2cdbcd" // should be in keystore but oh well



@ThreadLocal
class NetworkApi(private val endPoint: String) {

    private val platformLogger : Logger = CoolLogger()
    private class CoolLogger : Logger {
        override fun log(message: String) {
            Log.i(message)
        }
    }

    // Json.nonstrict is needed because API can change and it'll crash if a new key that's unknown is added
    // Add more setMapper  to the method as needed
    // Turn on Json.nonstrict (pass as argument in KotlinxSerializer constructor) for RELEASE builds, strict for debug !!!!!!!!
    @ThreadLocal
    private val httpClient = HttpClient {
        install(JsonFeature){
            serializer = KotlinxSerializer(Json.nonstrict).apply {
                setMapper(CurrentCityWeatherResponse::class, CurrentCityWeatherResponse.serializer()) // Add more of these for each response type
                setMapper(CurrentCityForecastResponse::class, CurrentCityForecastResponse.serializer())
            }
        }
        if (isDebug()) {
            install(Logging) {
                logger = platformLogger
                level = LogLevel.ALL
            }
        }

        // ExpectSuccess deprecated now use HttpValidator, also except success on by default
        //install(ExpectSuccess)
    }


    // API methods
    // The error for this method returns {"cod":"404","message":"city not found"}
    suspend fun getCurrentWeather(citySearchText: String): CurrentCityWeatherResponse = httpClient.get {
        url {
            protocol = URLProtocol.HTTPS
            host = WEATHER_HOST
            encodedPath = "data/2.5/weather"
            appendDefaultParameters(parameters)
            parameter("q", citySearchText)
        }
    }
    suspend fun getCurrentWeather(cityID: Int): CurrentCityWeatherResponse = httpClient.get {
        url {
            protocol = URLProtocol.HTTPS
            host = WEATHER_HOST
            encodedPath = "data/2.5/weather"
            appendDefaultParameters(parameters)
            parameter("id", cityID)
        }
    }
    suspend fun getCurrentForecast(citySearchText: String): CurrentCityForecastResponse = httpClient.get {
        url {
            protocol = URLProtocol.HTTPS
            host = WEATHER_HOST
            encodedPath = "data/2.5/forecast"
            appendDefaultParameters(parameters)
            parameter("q", citySearchText)
        }
    }
    suspend fun getCurrentForecast(cityID: Int): CurrentCityForecastResponse = httpClient.get {
        url {
            protocol = URLProtocol.HTTPS
            host = WEATHER_HOST
            encodedPath = "data/2.5/forecast"
            appendDefaultParameters(parameters)
            parameter("id", cityID)
        }
    }



    // Non API methods
    private fun appendDefaultParameters(parameters: ParametersBuilder) {
        parameters.append("appid", WEATHER_API_KEY)
        parameters.append("units", METRIC_UNITS)
    }
    private fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }
    private fun HttpRequestBuilder.apiUrl(path: String) {
        url {
            takeFrom(endPoint)
            encodedPath = path
        }
    }
}