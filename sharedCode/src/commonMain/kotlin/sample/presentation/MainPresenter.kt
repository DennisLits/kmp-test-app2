package sample.presentation

import com.github.florent37.livedata.KLifecycle
import com.github.florent37.livedata.KLiveData
import com.squareup.sqldelight.Query
import sample.Log
import sample.db.DBHelper
import sample.getMainDispatcher
import sample.launchAndCatch
import sample.networkModels.CurrentCityWeatherResponse
import sample.networkModels.MainDisplayData
import sample.networkModels.toDisplayModel
import kotlin.coroutines.CoroutineContext

class MainPresenter(private val view: MainView,
                    private val repository: DataRepository,
                    private val uiContext: CoroutineContext = getMainDispatcher(),
                    private val lifeCycleOwner: KLifecycle) : BasePresenter(lifeCycleOwner) {

    private val currentWeatherLiveData : KLiveData<MainDisplayData> = repository.getLData()

    private val cityIDSaved = 2172797

    init {

        currentWeatherLiveData.observe(lifeCycleOwner) {
            Log.i("Kotlin is cool! City name -> " + it.name)
            view.displayData(it)
        }

    }


    override fun onStart() {
        super.onStart()
        //listenForDBData(cityIDSaved) // TODO remove id later
        getDBDataJob()
    }

    override fun onStop() {
        super.onStop()
        //stopListenDBData(cityIDSaved)
    }

    /*
    private fun listenForDBData(cityID: Int) {
        DBHelper.getCurrentWeatherForCity(cityID).addListener(dbListener)
    }
    private fun stopListenDBData(cityID: Int){
        DBHelper.getCurrentWeatherForCity(cityID).removeListener(dbListener)
    }
    */
    private fun getDBDataJob(){

        launchAndCatch(uiContext, view::showError) {
            val dbWeather = DBHelper.getCurrentWeatherForCity(cityIDSaved).executeAsOne()
            //testLiveData.value = dbWeather.toDisplayModel()
            view.displayData(dbWeather.toDisplayModel())
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


        if(searchString.isNullOrEmpty()) {
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

    fun loadData(cityID: Int) {
        /*
        if (cityID.isNullOrEmpty()) {

            view.showError("No name todo remove this")
        } else {
              */
            view.showLoader()

            Log.i("111111 launching network job")



            launchAndCatch(uiContext, view::showError) {
                repository.refresh(cityID)
                //view.displayData(displayData)
            } finally {
                view.hideLoader()
            }
        //}
    }

    fun modifyDataForT() {

        view.showLoader()

        launchAndCatch(uiContext, view::showError) {
            repository.refreshFakeTestModify(cityIDSaved) // TODO change
            //view.displayData(displayData)
        } finally {
            view.hideLoader()
        }

    }




    // Internal for writing tests.
    internal fun getFakeDisplayData(response : CurrentCityWeatherResponse) : CurrentCityWeatherResponse {
        return response
    }

    /*
    internal fun getDisplayData(allData : AllData) = DisplayData(
        name = allData.name ?: allData.login,
        publicGists = "${allData.public_gists} $PUBLIC_GISTS",
        publicRepos = "${allData.public_repos} $PUBLIC_REPOS",
        avatarUrl = allData.avatar_url ?: DEFAULT_AVATAR,
        bio = allData.bio ?: NO_BIO_AVAILABLE
    )
    */
}