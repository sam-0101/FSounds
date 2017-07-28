package com.samuelepontremoli.fsounds.graphics

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by samuele on 03/07/17.
 * ImageView with height ratio setter
 */
class RatioImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr) {

    private var mHeightRatio: Float = 1f

    fun setHeightRatio(scale: Float) {
        mHeightRatio = scale
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mHeightRatio > 0.0f) {
            val width: Int = measuredWidth
            setMeasuredDimension(width, (width * mHeightRatio).toInt())
        }
    }

}