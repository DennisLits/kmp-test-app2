package sample.presentation
import sample.networkModels.MainDisplayData


interface MainView : BaseView {
    fun displayData(data: MainDisplayData)
    fun showLoader()
    fun hideLoader()
}