package com.wordpress.smdaudhilbe.nikah.custom.views

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by Mohammed Audhil on 18/06/17.
 * Jambav, Zoho Corp
 */

class VerticalViewPager : ViewPager {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun init() {
        setPageTransformer(true, VerticalPageTransformer())
        overScrollMode = View.OVER_SCROLL_NEVER
    }

    //  Vertical page transformer
    inner class VerticalPageTransformer : ViewPager.PageTransformer {

        override fun transformPage(view: View, position: Float) {
            if (position < -1) {
                view.alpha = 0f
            } else if (position <= 1) {
                view.alpha = 1f
                view.translationX = view.width * -position
                val yPosition = position * view.height
                view.translationY = yPosition
            } else {
                view.alpha = 0f
            }
        }
    }

    fun swapXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()
        val newX = ev.y / height * width
        val newY = ev.x / width * height
        ev.setLocation(newX, newY)
        return ev
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val intercepted = super.onInterceptTouchEvent(swapXY(ev))
        swapXY(ev)
        return intercepted
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(swapXY(ev))
    }
}