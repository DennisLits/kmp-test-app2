package sample.api

import io.ktor.client.HttpClient
import io.ktor.client.features.ExpectSuccess
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import kotlinx.serialization.json.Json
import sample.networkModels.CurrentCityWeatherResponse
import kotlin.native.concurrent.ThreadLocal


const val WEATHER_HOST = "api.openweathermap.org"

const val METRIC_UNITS = "metric"
const val WEATHER_API_KEY = "88feca02d25f9d3fde957167bb2cdbcd"



@ThreadLocal
class NetworkApi(private val endPoint: String) {


    // Json.nonstrict is needed because API can change and it'll crash if a new key that's unknown is added
    // Add more setMapper  to the method as needed
    // Turn on Json.nonstrict for RELEASE builds, strict for debug !!!!!!!!
    @ThreadLocal
    private val httpClient = HttpClient {
        install(JsonFeature){
            serializer = KotlinxSerializer().apply {
                setMapper(CurrentCityWeatherResponse::class, CurrentCityWeatherResponse.serializer()) // Add more of these for each response type
            }
        }
        install(ExpectSuccess)
    }

    // API methods
    // The error for this method returns {"cod":"404","message":"city not found"}
    suspend fun getCurrentWeather(citySearchText: String): CurrentCityWeatherResponse = httpClient.get {

        url {
            protocol = URLProtocol.HTTPS
            host = WEATHER_HOST
            encodedPath = "data/2.5/weather"
            parameter("q", citySearchText)
            parameter("appid", WEATHER_API_KEY)
            parameter("units", METRIC_UNITS)


        }

    }
    suspend fun getCurrentWeather(cityID: Int): CurrentCityWeatherResponse = httpClient.get {

        url {
            protocol = URLProtocol.HTTPS
            host = WEATHER_HOST
            encodedPath = "data/2.5/weather"
            parameter("id", cityID)
            parameter("appid", WEATHER_API_KEY)
            parameter("units", METRIC_UNITS)
        }

    }



    // Non API methods
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