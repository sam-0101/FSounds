package com.samuelepontremoli.fsounds.ui.detail

import com.samuelepontremoli.fsounds.commons.BasePresenter
import com.samuelepontremoli.fsounds.commons.BaseView
import com.samuelepontremoli.fsounds.network.SoundDetail
import com.samuelepontremoli.fsounds.network.SoundPartialDetail

/**
 * FSounds - Created by s.pontremoli on 26/07/2017.
 */
interface ISoundDetailContract {

    interface ISoundDetailView : BaseView<ISoundDetailPresenter> {

        fun onSoundLoadedSuccess(sound: SoundDetail)

        fun onSoundLoadedFailure(error: Throwable)

        fun onSoundLoadedComplete()

        fun showLoading()

        fun hideLoading()

        fun hideError()

        fun showError()

    }

    interface ISoundDetailPresenter : BasePresenter {

        fun loadSound()

        fun shouldPlayOrStopSound()

    }

}