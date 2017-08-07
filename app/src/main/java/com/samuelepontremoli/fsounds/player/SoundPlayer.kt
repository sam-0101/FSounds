package com.samuelepontremoli.fsounds.player

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


/**
 * FSounds - Created by s.pontremoli on 04/08/2017.
 */
class SoundPlayer(val playerView: ISoundPlayerContract.SoundPlayerView, context: Context) : ExoPlayer.EventListener, ISoundPlayerContract.SoundPlayerPresenter {

    private var player: SimpleExoPlayer

    private var isPlaying = false

    private val extractorsFactory by lazy { DefaultExtractorsFactory() }
    private val defaultBandwidthMeter by lazy { DefaultBandwidthMeter() }
    private val dataSourceFactory by lazy { DefaultDataSourceFactory(context, Util.getUserAgent(context, "mediaPlayerSample"), defaultBandwidthMeter) }

    init {
        val bandwidthMeter = DefaultBandwidthMeter()
        val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(trackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        player.addListener(this)
        playerView.setPlayerPresenter(this)
    }

    override fun setMediaSource(url: String) {
        val mediaSource = ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null)
        player.prepare(mediaSource)
    }

    override fun shouldPlayOrStopSound() {
        if (isPlaying) stopPlayback() else startPlayback()
    }

    override fun startPlayback() {
        player.playWhenReady = true
        isPlaying = true
//        playerView.onPlaySound()
    }

    override fun stopPlayback() {
        player.playWhenReady = false
        isPlaying = false
//        playerView.onStopSound()
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        error?.printStackTrace()
        playerView.onPlaybackError()
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
        playerView.onProgressUpdated()
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        //TODO
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        //TODO
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        Log.d("asd", playbackState.toString())
        if(playbackState == ExoPlayer.STATE_ENDED) {
            player.playWhenReady = false
            isPlaying = false
            player.seekTo(0,0)
            playerView.onPlaybackEnded()
        }
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        //TODO
    }

    override fun onPositionDiscontinuity() {
        //TODO
    }

}