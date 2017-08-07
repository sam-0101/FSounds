package com.samuelepontremoli.fsounds.player

/**
 * FSounds - Created by s.pontremoli on 04/08/2017.
 */
interface ISoundPlayerContract {

    interface SoundPlayerView {

//        fun onPlaySound()
//
//        fun onStopSound()

        fun onProgressUpdated()

        fun onPlaybackError()

        fun onPlaybackEnded()

        fun setPlayerPresenter(controller: SoundPlayerPresenter)

    }

    interface SoundPlayerPresenter {

        fun setMediaSource(url: String)

        fun shouldPlayOrStopSound()

        fun startPlayback()

        fun stopPlayback()

    }

}