package com.wordpress.smdaudhilbe.push_notification

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.iid.FirebaseInstanceId


/**
 * Created by Mohammed Audhil on 06/07/17.
 * Jambav, Zoho Corp
 */

class PushFirebaseInstanceIDService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.v(javaClass.name, "---Firebase Refreshed token: " + refreshedToken)
        sendRegistrationToAppServer(refreshedToken)
    }

    fun sendRegistrationToAppServer(refreshedToken: String?) {
        //  this is optional - sending token to the App Server(not Google's or Firebase's)
    }
}