package com.wordpress.smdaudhilbe.nikah.custom.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by Mohammed Audhil on 24/06/17.
 * Jambav, Zoho Corp
 */

class AcmeTextView : TextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun setTypeface(tf: Typeface?) {
        super.setTypeface(Typeface.createFromAsset(context.assets, "font/acme_regular.ttf"))
    }
}