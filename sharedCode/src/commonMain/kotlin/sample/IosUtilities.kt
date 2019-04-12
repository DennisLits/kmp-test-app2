package sample


class IosUtilities {

    /**
     * A hack to provide dispetcher to iOS
     */

    fun getDispetcher() = getMainDispatcher()
}