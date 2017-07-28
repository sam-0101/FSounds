package com.samuelepontremoli.fsounds.commons

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.samuelepontremoli.fsounds.R

/**
 * Created by s.pontremoli on 06/07/2017.
 * Base activity from which every activity inherits:
 * Can change fragments, handles backstack
 */
open class BaseActivity : AppCompatActivity() {

    fun changeFragment(f: Fragment, cleanStack: Boolean = false, tag: String) {
        val ft = supportFragmentManager.beginTransaction()
        if (cleanStack) {
            clearBackStack()
        }
        ft.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit)
        ft.replace(R.id.fragment_container, f, tag)
        ft.addToBackStack(null)
        ft.commit()
    }

    fun clearBackStack() {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            val first = manager.getBackStackEntryAt(0)
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    /**
     * Finish activity when reaching the last fragment.
     */
    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}