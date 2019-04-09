package sample.presentation

import com.github.florent37.livedata.KLifecycle
import com.github.florent37.livedata.KLiveData
import sample.*
import sample.api.Error
import sample.networkModels.CurrentCityWeatherResponse
import kotlin.coroutines.CoroutineContext

class MainPresenter(private val view: MainView,
                    private val repository: DataRepository,
                    private val uiContext: CoroutineContext = getMainDispetcher(),
                    private val lifeCycleOwner: KLifecycle) {

    val testLiveData : KLiveData<CurrentCityWeatherResponse> = repository.getData()



    fun loadData(cityID: String) {
        if (cityID.isNullOrEmpty()) {
            view.showError("No name todo remove this")
        } else {
            view.showLoader()

            Log.i("111111")


            // TODO MOVE THIS SOMEWHERE BETTER
            testLiveData.observe(lifeCycleOwner) {
                Log.i("Kotlin is cool! City name -> " + it.name)
            }

            launchAndCatch(uiContext, view::showError) {
                repository.refresh(cityID)
                //showData()
            } finally {
                view.hideLoader()
            }
        }
    }

    fun modifyDataTest(cityID: String) {

        view.showLoader()

        launchAndCatch(uiContext, view::showError) {
            repository.refreshFakeTestModify(cityID) // TODO change
            //showData()
        } finally {
            view.hideLoader()
        }

    }



    /*
    private fun showData() {
         repository.data?.let {
           view.displayData(getFakeDisplayData(it))
        }
    }
    */


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