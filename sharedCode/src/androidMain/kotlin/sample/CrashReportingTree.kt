package sample


import java.lang.reflect.Array.setInt
import timber.log.Timber


class CrashReportingTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == android.util.Log.VERBOSE || priority == android.util.Log.DEBUG) {
            return
        }

        val t = throwable ?: Exception(message)

        // TODO implement crash reporting
        /*
        // Crashlytics
        Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
        Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
        Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)
        Crashlytics.logException(t)

        // Firebase Crash Reporting
        FirebaseCrash.logcat(priority, tag, message)
        FirebaseCrash.report(t)
        */
    }

    companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"
        private const val CRASHLYTICS_KEY_TAG = "tag"
        private const val CRASHLYTICS_KEY_MESSAGE = "message"
    }
}