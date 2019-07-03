package sample.tests


import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sample.api.NetworkApi
import sample.api.WEATHER_HOST
import sample.networkModels.*
import kotlin.test.Test
import kotlin.test.assertEquals


open class AllUnitTests {




    @Test
    fun testGetWeather() {

        val api = NetworkApi(WEATHER_HOST)

        coEvery{ api.getCurrentWeather(1) } returns
                getFakeWeatherResp()



        // fix


        GlobalScope.launch {
            val resp = api.getCurrentWeather(1)
            print(resp)
        }





    }

    fun getFakeWeatherResp() : CurrentCityWeatherResponse {

        return CurrentCityWeatherResponse(
            base = "mont base",
            clouds = Clouds(all = 0),
            dt = 0,
            id = 1,
            main = Main(humidity = 1, pressure = 2.0, temp = 16.0, temp_min = 2.0, temp_max = 20.0),
            name = "Montreal",
            weather = listOf(),
            wind = Wind(deg = 3.0, speed = 12.32)
        )
    }







}

