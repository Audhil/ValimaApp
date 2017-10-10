package com.wordpress.smdaudhilbe.nikah.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wordpress.smdaudhilbe.nikah.R
import kotlinx.android.synthetic.main.place_holder_frag.*
import android.text.TextPaint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.style.ClickableSpan
import android.text.SpannableString
import android.text.method.LinkMovementMethod

class PlaceHolderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.place_holder_frag, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (arguments?.getInt(PAGE_NUMBER)) {

            1 -> {  //  save the date
                save_the_date_layout.animate().alpha(1f).setDuration(1000).start()
                thankYouTextView.visibility = View.GONE
            }
            3 -> {  //  details base layout
                details_base_layout.animate().alpha(1f).setDuration(1000).start()
                val ss = SpannableString(getString(R.string.locationActual))

                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(textView: View) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("http://www.bit.ly/msmahal")
                        startActivity(intent)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                        ds.color = ContextCompat.getColor(activity, R.color.text_blue)
                    }
                }
                ss.setSpan(clickableSpan, 0, ss.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                locationBitlyTxtView.text = ss
                locationBitlyTxtView.movementMethod = LinkMovementMethod.getInstance()
                thankYouTextView.visibility = View.GONE
            }
            5 -> {
                thankYouTextView.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private val PAGE_NUMBER = "page_number"
        fun newInstance(pageNumber: Int): PlaceHolderFragment {
            val fragment = PlaceHolderFragment()
            val args = Bundle()
            args.putInt(PAGE_NUMBER, pageNumber)
            fragment.arguments = args
            return fragment
        }
    }
}