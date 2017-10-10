package com.wordpress.smdaudhilbe.nikah

import android.app.Application
import com.wordpress.smdaudhilbe.nikah.util.PreferenceUtil

/**
 * Created by Mohammed Audhil on 11/06/17.
 * Jambav, Zoho Corp
 */

class AppApplication : Application() {

    companion object {
        lateinit var applicationInstance: AppApplication
            private set

        lateinit var applicationPreference: PreferenceUtil

        var PREF_NAME: String = "NikahPreference"
        var PASSWORD: String = "Password"

        var USER_NAMES: String = "UserNames"
        var COLON: String = ":"
    }

    override fun onCreate() {
        super.onCreate()
        applicationInstance = this
        applicationPreference = PreferenceUtil()
    }
}