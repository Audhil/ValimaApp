package com.wordpress.smdaudhilbe.nikah.util

import android.content.Context
import android.content.SharedPreferences
import com.wordpress.smdaudhilbe.nikah.AppApplication
import com.wordpress.smdaudhilbe.nikah.AppApplication.Companion.COLON
import com.wordpress.smdaudhilbe.nikah.AppApplication.Companion.PASSWORD
import com.wordpress.smdaudhilbe.nikah.AppApplication.Companion.PREF_NAME
import com.wordpress.smdaudhilbe.nikah.AppApplication.Companion.USER_NAMES

/**
 * Created by Mohammed Audhil on 11/06/17.
 * Jambav, Zoho Corp
 */

class PreferenceUtil {
    var pref: SharedPreferences = AppApplication.applicationInstance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = pref.edit()

    fun getPassword(): String {
        LogUtil.d(javaClass.name, "--- getPassword success--- : " + pref.getString(PASSWORD, ""))
        return pref.getString(PASSWORD, "")
    }

    fun saveRegisteredUsers(name: String, pwd: String) {
        var oldValue = pref.getString(USER_NAMES, "")
        oldValue += COLON + name + COLON + pwd
        editor.putString(USER_NAMES, name)
        editor.putString(PASSWORD, pwd)
        editor.apply()
        LogUtil.d(javaClass.name, "--- saveRegisteredUsers success--- ")
    }
}