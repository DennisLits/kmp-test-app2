package sample.presentation

import com.github.florent37.livedata.KLifecycle
import com.github.florent37.livedata.KLiveData
import sample.*
import sample.db.DBHelper
import sample.networkModels.CurrentWeatherDisplayData
import sample.networkModels.ForecastDisplayData
import sample.networkModels.toDisplayModel
import sample.utils.SettingsUtils
import kotlin.coroutines.CoroutineContext

class WeatherPresenter(private val view: MainView,
                       private val repository: DataRepository,
                       private val uiContext: CoroutineContext = getMainDispatcher(),
                       private val lifeCycleOwner: KLifecycle) : BasePresenter(lifeCycleOwner) {

    private val currentWeatherLiveData : KLiveData<CurrentWeatherDisplayData> = repository.getLiveCurrentData()
    private val forecastLiveData : KLiveData<ForecastDisplayData> = repository.getLiveForecastData()

    private var cityIDSaved : Int? = null
    //private var activeQuery : Query<CurrentWeather>? = null
    private var shouldCheckNetworkAfterDB = false

    init {

        // live data actually wants 2 seperate calls of kLifecycle, 1 for each livedata, I think commenting out a line in library and re-using both is fine for android (and more correct)
        // Check KLiveData line 29 android implementation

      currentWeatherLiveData.observe(lifeCycleOwner) {
          Log.i("Kotlin is cool! City name -> " + it.name)
          view.displayCurrentData(it)
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

      forecastLiveData.observe(lifeCycleOwner) {
          Log.i("Forecast data observed -> temp " + it.city.list[0].main.temp + " degrees C")
          view.displayForecastData(it)
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
        val lastSearchedCityID = SettingsUtils.getLastUserSearchedCityID()


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
            view.displayCurrentData(dbWeather.toDisplayModel(false))

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
            repository.getCityCurrentWeatherBySearch(searchString)
        } finally {
            view.hideLoader()
        }


    }

    private fun loadDataSilent(cityID: Int) {

        Log.i("111111 launching network job")

        launchAndCatch(uiContext, view::showError) {
            repository.getCityCurrentWeatherByID(cityID)
            //view.displayData(displayData)
        } finally {
            view.hideLoader()
        }

    }





}