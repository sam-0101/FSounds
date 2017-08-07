package com.samuelepontremoli.fsounds.ui.search

import com.samuelepontremoli.fsounds.commons.BasePresenter
import com.samuelepontremoli.fsounds.commons.BaseView
import com.samuelepontremoli.fsounds.network.SoundPartialDetail

/**
 * FSounds - Created by s.pontremoli on 24/07/2017.
 */
interface ISoundSearchContract {

    interface ISoundSearchView : BaseView<ISoundSearchPresenter> {

        fun onSoundsLoadedSuccess(list: List<SoundPartialDetail>)

        fun onSoundsLoadedFailure(error: Throwable)

        fun onSoundsLoadedComplete()

        fun emptySounds()

        fun showLoading()

        fun hideLoading()

        fun hideError()

        fun showError()

    }

    interface ISoundSearchPresenter : BasePresenter {

        fun loadSoundsFromText()

        fun loadSoundsFromTag()

    }

}