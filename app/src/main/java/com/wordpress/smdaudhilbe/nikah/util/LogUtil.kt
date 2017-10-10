package com.wordpress.smdaudhilbe.nikah.util

import android.util.Log

/**
 * Created by Mohammed Audhil on 11/06/17.
 * Jambav, Zoho Corp
 */

object LogUtil {
    var DEBUG_MODE: Boolean = true
    fun i(tag: String, msg: String) {
        if (DEBUG_MODE) {
            Log.i(tag + "CHECKK", msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (DEBUG_MODE) {
            Log.w(tag + "CHECKK", msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (DEBUG_MODE) {
            Log.e(tag + "CHECKK", msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (DEBUG_MODE) {
            Log.d(tag + "CHECKK", msg)
        }
    }

    fun v(tag: String, msg: String) {
        if (DEBUG_MODE) {
            Log.v(tag + "CHECKK", msg)
        }
    }
}