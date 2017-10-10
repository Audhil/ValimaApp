package com.wordpress.smdaudhilbe.nikah.util

/**
 * Created by Mohammed Audhil on 17/06/17.
 * Jambav, Zoho Corp
 */

fun CharSequence.isOnlyAlphabets(): Boolean {
    return this.matches(Regex("^[a-zA-Z ]*$"))
}

fun CharSequence.isValidEmailId(): Boolean {
    return this.matches(Regex("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
}