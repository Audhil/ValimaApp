package com.wordpress.smdaudhilbe.nikah.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.wordpress.smdaudhilbe.nikah.AppApplication
import com.wordpress.smdaudhilbe.nikah.R
import com.wordpress.smdaudhilbe.nikah.ViewPagerActivity
import com.wordpress.smdaudhilbe.nikah.commlisteners.FragToActivityListener
import com.wordpress.smdaudhilbe.nikah.model.UserData
import com.wordpress.smdaudhilbe.nikah.util.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Mohammed Audhil on 19/06/17.
 * Jambav, Zoho Corp
 */

class FeedbackFragment : Fragment() {

    companion object {
        fun newInstance(): FeedbackFragment = FeedbackFragment()
    }

    lateinit var iListener: FragToActivityListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let {
            if (context is ViewPagerActivity) {
                iListener = context
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater?.inflate(R.layout.feedback_frag, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(activity)
        wish_button.setOnClickListener {
            sendWishesClick(wish_button)
        }
    }

    var progressDialog: ProgressDialog? = null

    var mFirebaseAuth = FirebaseAuth.getInstance()
    var mFirebaseUser = mFirebaseAuth.currentUser
    var mDatabase = FirebaseDatabase.getInstance().reference

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
                Toast.makeText(context, getString(R.string.enterAllFields), Toast.LENGTH_SHORT).show()
                return
            }
            if (!email_edit_text.text.trim().isValidEmailId()) {
                Toast.makeText(context, getString(R.string.enterValidEmailAddress), Toast.LENGTH_SHORT).show()
                return
            }
            if (!name_edit_text.text.trim().isOnlyAlphabets()) {
                Toast.makeText(context, getString(R.string.enterValidName), Toast.LENGTH_SHORT).show()
                return
            }
            if (!isInternetAvailable()) {
                Toast.makeText(context, getString(R.string.networkNotAvailable), Toast.LENGTH_SHORT).show()
                return
            }
            Toast.makeText(context, getString(R.string.enterAllFields), Toast.LENGTH_SHORT).show()
            return
        }
        showProgressDialog()
        val passWord = generateRandomPassword()
        mFirebaseAuth.createUserWithEmailAndPassword(email_edit_text.text.trim().toString(), passWord)
                .addOnCompleteListener(activity) { task ->
                    hideProgressDialog()
                    getNewInstances()
                    if (task.isSuccessful) {
                        AppApplication.applicationPreference.saveRegisteredUsers(name_edit_text.text.trim().toString(), passWord)
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
        if (mFirebaseAuth == null
                || mFirebaseUser == null
                || mDatabase == null) {
            Toast.makeText(context, getString(R.string.someErrorOccurred), Toast.LENGTH_LONG).show()
            return
        }
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
//        Toast.makeText(context, getString(R.string.thankYouMessage), Toast.LENGTH_LONG).show()
        iListener.wishGiven()
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
    fun hideProgressDialog() = progressDialog?.dismiss()
}