package sample.presentation

import com.fhyber.multiweather.data.CurrentWeather
import com.github.florent37.livedata.KLifecycle
import com.github.florent37.livedata.KLiveData
import com.squareup.sqldelight.Query
import sample.*
import sample.constants.SettingsKeys
import sample.db.DBHelper
import sample.networkModels.CurrentCityWeatherResponse
import sample.networkModels.MainDisplayData
import sample.networkModels.toDisplayModel
import kotlin.coroutines.CoroutineContext

class MainPresenter(private val view: MainView,
                    private val repository: DataRepository,
                    private val uiContext: CoroutineContext = getMainDispatcher(),
                    private val lifeCycleOwner: KLifecycle) : BasePresenter(lifeCycleOwner) {

    private val currentWeatherLiveData : KLiveData<MainDisplayData> = repository.getLData()

    private var cityIDSaved : Int? = null
    //private var activeQuery : Query<CurrentWeather>? = null
    private var shouldCheckNetworkAfterDB = false

    init {

        currentWeatherLiveData.observe(lifeCycleOwner) {
            Log.i("Kotlin is cool! City name -> " + it.name)
            view.displayData(it)
            cityIDSaved = it.city_id

            // Listen to NEW city get rid of old city listener
            /*
            if (cityIDSaved != null && cityIDSaved != it.city_id) {
                stopListenDBData()
                cityIDSaved = it.city_id
                listenForDBData(cityIDSaved!!)
            }
            else if (cityIDSaved == null) {
                cityIDSaved = it.city_id
                listenForDBData(cityIDSaved!!)
            }*/
        }


        checkForSavedCityID()

    }


    override fun onStart() {
        super.onStart()

        //if(cityIDSaved != null)
        //    listenForDBData(cityIDSaved!!) // TODO remove id later

        getDBDataJob()
    }

    override fun onStop() {
        super.onStop()

        //stopListenDBData()
    }

    /*
    private fun listenForDBData(cityID: Int) {
        activeQuery = DBHelper.getCurrentWeatherForCity(cityID)
        activeQuery!!.addListener(dbListener)
    }
    private fun stopListenDBData(){
        if(activeQuery != null) {
            activeQuery!!.removeListener(dbListener)
        }
    }
    */


    private fun checkForSavedCityID() {
        val lastSearchedCityID = settings.getInt(SettingsKeys.LAST_SEARCH, -1)


        if(lastSearchedCityID != -1) {
            cityIDSaved = lastSearchedCityID
            shouldCheckNetworkAfterDB = true
        }
    }

    private fun getDBDataJob(){

        if (cityIDSaved == null)
            return

        launchAndCatch(uiContext, view::showError) {
            val dbWeather = DBHelper.getCurrentWeatherForCity(cityIDSaved!!).executeAsOne()
            //testLiveData.value = dbWeather.toDisplayModel()
            view.displayData(dbWeather.toDisplayModel(false))

            if (shouldCheckNetworkAfterDB) {
                shouldCheckNetworkAfterDB = false
                loadDataSilent(cityIDSaved!!)

            }

        } finally {

        }

    }

    /*
    private val dbListener = object : Query.Listener{
        override fun queryResultsChanged() {
            Log.i("!!DB CHANGED!!")
            getDBDataJob()
        }
    }
    */


    fun loadData(searchString: String) {


        if (searchString.isNullOrEmpty()) {
            view.showError("error empty")
            return
        }


        view.showLoader()
        Log.i("111111 launching network job")
        launchAndCatch(uiContext, view::showError) {
            repository.searchCity(searchString)
        } finally {
            view.hideLoader()
        }


    }

    fun loadDataSilent(cityID: Int) {

        Log.i("111111 launching network job")

        launchAndCatch(uiContext, view::showError) {
            repository.refresh(cityID)
            //view.displayData(displayData)
        } finally {
            view.hideLoader()
        }

    }





}