package sample.tests


import sample.api.NetworkApi
import sample.api.WEATHER_HOST
import kotlin.test.Test
import kotlin.test.assertEquals


open class AllUnitTests {



    @Test
    fun emptyTest() {

        assertEquals(0, 0)


    }

    @Test
    fun testGetWeather() {

        val api = NetworkApi(WEATHER_HOST)


    }



}

