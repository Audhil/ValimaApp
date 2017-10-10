package com.wordpress.smdaudhilbe.nikah

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.wordpress.smdaudhilbe.nikah.model.UserData
import kotlinx.android.synthetic.main.activity_main.*
import com.wordpress.smdaudhilbe.nikah.util.*
import java.text.SimpleDateFormat
import java.util.*
import android.os.CountDownTimer
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var progressDialog: ProgressDialog? = null

    var mFirebaseAuth = FirebaseAuth.getInstance()
    var mFirebaseUser = mFirebaseAuth.currentUser
    var mDatabase = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressDialog = ProgressDialog(this)
        startCountDownTimer()
    }

    //  start count down timer
    fun startCountDownTimer() {
        val futureCal = Calendar.getInstance()
        futureCal.set(2017, Calendar.JULY, 29, 11, 30)
        val millisToTick = futureCal.timeInMillis - Calendar.getInstance().timeInMillis
        if (millisToTick < 0) {
            Toast.makeText(applicationContext, "Event over!", Toast.LENGTH_LONG).show()
            return
        }
        object : CountDownTimer(millisToTick, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer_txt_view.text = "Days : " + (TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))).toString() + "\n"
                timer_txt_view.append("Hours : " + (TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished))).toString() + "\n")
                timer_txt_view.append("Mins : " + (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))).toString() + "\n")
                timer_txt_view.append("Seconds : " + (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))).toString() + "\n")
            }

            override fun onFinish() {
            }
        }.start()
    }

    //  OnClick of button
    fun sendWishesClick(view: View) {
        if (email_edit_text.text.trim().isBlank()
                || !email_edit_text.text.trim().isValidEmailId()
                || name_edit_text.text.trim().isBlank()
                || wish_edit_text.text.trim().isBlank()
                || !name_edit_text.text.trim().isOnlyAlphabets()
                || !isInternetAvailable()) {
            if (email_edit_text.text.trim().isBlank()
                    || name_edit_text.text.trim().isBlank()
                    || wish_edit_text.text.trim().isBlank()) {
                Toast.makeText(applicationContext, getString(R.string.enterAllFields), Toast.LENGTH_SHORT).show()
                return
            }
            if (!email_edit_text.text.trim().isValidEmailId()) {
                Toast.makeText(applicationContext, getString(R.string.enterValidEmailAddress), Toast.LENGTH_SHORT).show()
                return
            }
            if (!name_edit_text.text.trim().isOnlyAlphabets()) {
                Toast.makeText(applicationContext, getString(R.string.enterValidName), Toast.LENGTH_SHORT).show()
                return
            }
            if (!isInternetAvailable()) {
                Toast.makeText(applicationContext, getString(R.string.networkNotAvailable), Toast.LENGTH_SHORT).show()
                return
            }
            Toast.makeText(applicationContext, getString(R.string.enterAllFields), Toast.LENGTH_SHORT).show()
            return
        }
        showProgressDialog()
        val passWord = generateRandomPassword()
        mFirebaseAuth.createUserWithEmailAndPassword(email_edit_text.text.trim().toString(), passWord)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        AppApplication.applicationPreference.saveRegisteredUsers(name_edit_text.text.trim().toString(), passWord)
                        getNewInstances()
                        pushDataToServer()
                        LogUtil.d(javaClass.name, "---user putting data to server--- ")
                    } else {
                        LogUtil.d(javaClass.name, "---user register task failed--- " + task.exception?.message)
                        if (task.exception?.message == (getString(R.string.emailIdAlreadyExists))) {
                            LogUtil.d(javaClass.name, "---user already exists hence data updated--- ")
                            pushDataToServer()
                        }
                    }
                }
    }

    //  push data to server
    fun pushDataToServer() {
        val wishersRef = mDatabase.child("wishers").child(mFirebaseUser?.uid).push()
        val wishers = HashMap<String, UserData>()
        wishers.put(name_edit_text.text.trim().toString(),
                UserData(mFirebaseUser?.uid,
                        email_edit_text.text.trim().toString(),
                        AppApplication.applicationPreference.getPassword(),
                        wish_edit_text.text.trim().toString(),
                        SimpleDateFormat(getString(R.string.timeDateYearFormat), Locale.getDefault()).format(System.currentTimeMillis())))
        wishersRef.setValue(wishers)
        LogUtil.d(javaClass.name, "---user data pushed--- wishers : " + wishers)
        Toast.makeText(applicationContext, getString(R.string.thankYouMessage), Toast.LENGTH_LONG).show()
    }

    //  getting new instances
    private fun getNewInstances() {
        mFirebaseUser = null
        mDatabase = null
        mFirebaseUser = mFirebaseAuth.currentUser
        mDatabase = FirebaseDatabase.getInstance().reference
    }

    //  show progress dialog
    fun showProgressDialog() {
        progressDialog?.setMessage("Please wait...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    //  hide progress dialog
    fun hideProgressDialog() {
        progressDialog?.dismiss()
    }
}