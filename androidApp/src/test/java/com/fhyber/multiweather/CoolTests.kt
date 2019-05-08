package com.fhyber.multiweather

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

import org.robolectric.RuntimeEnvironment
import com.fhyber.multiweather.data.CurrentWeather
import junit.framework.Assert.assertEquals
import org.junit.Test

import sample.db.DBHelper
import sample.getDatabaseDriverFromContext


@RunWith(RobolectricTestRunner::class)
class CoolTests {

    @Test
    fun testDatabase() {

        // Deprecated but this is JVM not android?
        val testContext = RuntimeEnvironment.application
        val testDB = WeatherDb(getDatabaseDriverFromContext(testContext))


        testDB.weatherQueries.clear()

        assertEquals(DBHelper.countAllRows(), 0)

        val currentWeather = getTestCurrentWeather()
        testDB.weatherQueries.insertCityModel(currentWeather)

        assertEquals(DBHelper.countAllRows(), 1)

        val modelFromDB = DBHelper.getCurrentWeatherForCity(testCityId).executeAsOne()

        assertEquals(modelFromDB.city_id, testCityId)
        assertEquals(modelFromDB.name, testCityName)
        assertEquals(modelFromDB.temp, testCityTemperature)

        testDB.weatherQueries.clear()

        assertEquals(DBHelper.countAllRows(), 0)




    }


    val testCityName = "test city"
    val testCityId = 2
    val testCityTemperature = 20.2

    private fun getTestCurrentWeather() : CurrentWeather {
        return CurrentWeather.Impl(city_id = testCityId, name = testCityName, pressure = 0.0, humidity = 0, temp = testCityTemperature, time = 10)
    }

}