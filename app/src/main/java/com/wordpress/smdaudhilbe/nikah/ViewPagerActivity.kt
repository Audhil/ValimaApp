package com.wordpress.smdaudhilbe.nikah

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.FragmentPagerAdapter
import android.animation.ArgbEvaluator
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.view.View
import com.wordpress.smdaudhilbe.nikah.commlisteners.FragToActivityListener
import com.wordpress.smdaudhilbe.nikah.fragments.FeedbackFragment
import com.wordpress.smdaudhilbe.nikah.fragments.PlaceHolderFragment
import com.wordpress.smdaudhilbe.nikah.fragments.TimerFragment


/**
 * Created by Mohammed Audhil on 18/06/17.
 * Jambav, Zoho Corp
 *
 */

class ViewPagerActivity : AppCompatActivity(), FragToActivityListener {

    var isWishGiven: Boolean = false

    override fun wishGiven() {
        isWishGiven = true
        mSectionsPagerAdapter.notifyDataSetChanged()
        mViewPager.setCurrentItem(4, true)
    }

    lateinit var mSectionsPagerAdapter: SectionsPagerAdapter
    lateinit var mViewPager: ViewPager
    lateinit var colors: Array<Int>
    var argbEvaluator = ArgbEvaluator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        mViewPager = findViewById(R.id.verti_view_pager) as ViewPager
        mViewPager.adapter = mSectionsPagerAdapter
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 3) {    //  removing the last itemr
                    isWishGiven = false
                    mSectionsPagerAdapter.notifyDataSetChanged()
                }

                when (position) {
                    0 -> {
                        setSystemUI()
                    }
                    1 -> {
                        setSystemUI()
                    }
                    2 -> {
                        setSystemUI()
                    }
                    3 -> {
                        setSystemUI()
                    }
                    4 -> {
                        setSystemUI()
                    }
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position < mSectionsPagerAdapter.count - 1 && position < colors.size - 1) {
                    mViewPager.setBackgroundColor(argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]) as Int)
                } else {
                    mViewPager.setBackgroundColor(colors[colors.size - 1])
                }
            }
        })
        setUpColors()
        setSystemUI()
    }

    //  set Status bar color
    fun setSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    //  setup colors
    val color1 by lazy {
        ContextCompat.getColor(applicationContext, R.color.color1)
    }

    val color2 by lazy {
        ContextCompat.getColor(applicationContext, R.color.color2)
    }

    val color3 by lazy {
        ContextCompat.getColor(applicationContext, R.color.color3)
    }

    val color5 by lazy {
        ContextCompat.getColor(applicationContext, R.color.color5)
    }

    fun setUpColors() {
        colors = arrayOf(color1, color2, color3, color5)
    }

    //  View pager adapter
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                1 -> {
                    return TimerFragment.newInstance()
                }
                3 -> {
                    return FeedbackFragment.newInstance()
                }
                else -> {
                    return PlaceHolderFragment.newInstance(position + 1)
                }
            }
        }

        override fun getCount(): Int {
            if (!isWishGiven)
                return 4
            else
                return 5
        }

        override fun getPageTitle(position: Int): CharSequence {
            return position.toString()
        }
    }
}