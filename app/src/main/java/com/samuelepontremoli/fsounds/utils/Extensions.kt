package com.samuelepontremoli.fsounds.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by samuele on 01/07/17.
 * Android extensions
 */

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ImageView.loadFromUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

fun Double.round(places: Int): Double {
    if (places < 0) throw IllegalArgumentException()
    return BigDecimal(this).setScale(places, RoundingMode.HALF_UP).toDouble()
}