package com.samuelepontremoli.fsounds.commons

/**
 * Created by s.pontremoli on 10/07/2017.
 * The base MVP Presenter
 * Has subscribe and unsubscribe because of RxJava
 */
interface BasePresenter {

    fun subscribe()

    fun unsubscribe()

}