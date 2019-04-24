package sample.utils

import com.russhwolf.settings.set
import sample.constants.SettingsKeys
import sample.settings

object SettingsUtils {


    fun setLastUserSearchedCityID(cityID : Int) {
        settings[SettingsKeys.LAST_SEARCH] = cityID
    }
    fun getLastUserSearchedCityID() : Int {
        return settings.getInt(SettingsKeys.LAST_SEARCH, -1)
    }
}