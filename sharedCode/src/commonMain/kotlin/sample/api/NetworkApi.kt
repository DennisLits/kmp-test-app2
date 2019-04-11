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
import sample.networkModels.CurrentCityWeatherResponse


const val WEATHER_HOST = "samples.openweathermap.org"
const val WEATHER_API_KEY = "88feca02d25f9d3fde957167bb2cdbcd"


class NetworkApi(private val endPoint: String) {



    //

    private val httpClient = HttpClient {
        install(JsonFeature){
            serializer = KotlinxSerializer().apply {
                setMapper(CurrentCityWeatherResponse::class, CurrentCityWeatherResponse.serializer())
            }
        }
        install(ExpectSuccess)
    }

    suspend fun getCurrentWeather(cityID: Int): CurrentCityWeatherResponse = httpClient.get {
        url {
            protocol = URLProtocol.HTTPS
            host = WEATHER_HOST
            encodedPath = "data/2.5/weather"
            parameter("id", cityID)
            parameter("appid", WEATHER_API_KEY)
        }
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