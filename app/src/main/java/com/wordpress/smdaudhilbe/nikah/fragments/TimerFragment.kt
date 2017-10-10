package com.wordpress.smdaudhilbe.nikah.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wordpress.smdaudhilbe.nikah.R
import com.wordpress.smdaudhilbe.nikah.util.LogUtil
import kotlinx.android.synthetic.main.timer_frag.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Mohammed Audhil on 18/06/17.
 * Jambav, Zoho Corp
 */

class TimerFragment : Fragment() {

    companion object {
        fun newInstance(): TimerFragment {
            return TimerFragment()
        }
    }

    var tempNo = 0
    var tempF = 0f
    var millisToTick = 0L
    lateinit var timer: CountDownTimer

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.timer_frag, container, false)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        startCountDownTimer()
    }

    //  start count down timer
    fun startCountDownTimer() {
        val futureCal = Calendar.getInstance()
        futureCal.set(2017, Calendar.JULY, 30, 19, 0)
        millisToTick = futureCal.timeInMillis - Calendar.getInstance().timeInMillis
        if (millisToTick < 0) {
            Toast.makeText(context, "Event over!", Toast.LENGTH_LONG).show()
            return
        }
        timer = object : CountDownTimer(millisToTick, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tempNo = TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)).toInt()
                daysTextView.text = resources.getQuantityText(R.plurals.dayKind, tempNo)
                tempF = tempNo / 10f
                daysNoTextViewFirstDigit.text = tempF.toString().split(".")[0]
                daysNoTextView.text = tempF.toString().split(".")[1]

                tempNo = (TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished))).toInt()
                hrsTextView.text = resources.getQuantityText(R.plurals.hrKind, tempNo)
                tempF = tempNo / 10f
                hrsNoTextViewFirstDigit.text = tempF.toString().split(".")[0]
                hrsNoTextView.text = tempF.toString().split(".")[1]


                tempNo = (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))).toInt()
                minsTextView.text = resources.getQuantityText(R.plurals.minKind, tempNo)
                tempF = tempNo / 10f
                minsNoTextViewFirstDigit.text = tempF.toString().split(".")[0]
                minsNoTextView.text = tempF.toString().split(".")[1]

                tempNo = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))).toInt()
                tempF = tempNo / 10f
                secsNoTextViewFirstDigit.text = tempF.toString().split(".")[0]
                secsNoTextView.text = tempF.toString().split(".")[1]
            }

            override fun onFinish() {
                daysNoTextView.text = getString(R.string.zero)
                hrsNoTextView.text = getString(R.string.zero)
                minsNoTextView.text = getString(R.string.zero)
                secsNoTextView.text = getString(R.string.zero)
                daysNoTextViewFirstDigit.text = getString(R.string.zero)
                hrsNoTextViewFirstDigit.text = getString(R.string.zero)
                minsNoTextViewFirstDigit.text = getString(R.string.zero)
                secsNoTextViewFirstDigit.text = getString(R.string.zero)

                daysTextView.text = getString(R.string.days)
                hrsTextView.text = getString(R.string.hrs)
                minsTextView.text = getString(R.string.mins)
            }
        }
        timer.start()
    }
}