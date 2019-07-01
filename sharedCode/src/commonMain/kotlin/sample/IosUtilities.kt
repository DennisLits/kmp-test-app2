package sample


class IosUtilities {

    /**
     * A hack to provide dispatcher to iOS
     */

    fun getIOSDispatcher() = getMainDispatcher()
}