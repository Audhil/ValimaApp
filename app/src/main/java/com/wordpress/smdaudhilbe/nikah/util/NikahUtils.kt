package com.wordpress.smdaudhilbe.nikah.util

import android.content.Context
import android.net.ConnectivityManager
import com.wordpress.smdaudhilbe.nikah.AppApplication

/**
 * Created by Mohammed Audhil on 11/06/17.
 * Jambav, Zoho Corp
 */

//  generate random password
fun generateRandomPassword(): String {
    val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    var passWord = ""
    for (i in 0..31) {
        passWord += chars[Math.floor(Math.random() * chars.length).toInt()]
    }
    return passWord
}

//  isInternet available
fun isInternetAvailable(): Boolean {
    var isWifiConnected = false
    var isMobileConnected = false
    val connectivityManager = AppApplication.applicationInstance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    activeNetwork?.let {
        if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
            isWifiConnected = true
        } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
            isMobileConnected = true
        }
    }
    return isWifiConnected || isMobileConnected
}