package com.samuelepontremoli.fsounds.commons

/**
 * Created by s.pontremoli on 10/07/2017.
 * The base MVP View
 */
interface BaseView<in T : BasePresenter> {

    fun setPresenter(presenter: T)

}