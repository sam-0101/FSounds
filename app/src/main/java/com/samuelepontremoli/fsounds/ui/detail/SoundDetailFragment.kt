package com.samuelepontremoli.fsounds.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.rijckaert.tim.animatedvector.FloatingMusicActionButton
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.samuelepontremoli.fsounds.R
import com.samuelepontremoli.fsounds.network.SoundDetail
import com.samuelepontremoli.fsounds.player.ISoundPlayerContract
import com.samuelepontremoli.fsounds.ui.MainActivity
import com.samuelepontremoli.fsounds.utils.loadFromUrl
import com.samuelepontremoli.fsounds.utils.makeGone
import com.samuelepontremoli.fsounds.utils.makeVisible
import com.samuelepontremoli.fsounds.utils.round
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*


/**
 * FSounds - Created by s.pontremoli on 26/07/2017.
 */
class SoundDetailFragment : Fragment(), ISoundDetailContract.ISoundDetailView, ISoundPlayerContract.SoundPlayerView, SoundTagsAdapter.TagItemClickListener {

    private var presenter: SoundDetailPresenter? = null

    private var playerPresenter: ISoundPlayerContract.SoundPlayerPresenter? = null

    private var soundDetailAdapter: SoundTagsAdapter? = null

    companion object {
        val TAG = "SoundDetailFragment"
        val ROUND_POSITIONS = 2
        val DETAIL_IMG_HEIGHT = 201
        val DETAIL_IMG_WIDTH = 900
        fun newInstance(): SoundDetailFragment {
            return SoundDetailFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        loadingView.makeGone()
        errorView.makeGone()
        initPlayPause()
        initTagView()
        initInfoView()
    }

    private fun initPlayPause() {
        playPause.changeMode(FloatingMusicActionButton.Mode.PLAY_TO_STOP)
        playPause.setOnMusicFabClickListener(object : FloatingMusicActionButton.OnMusicFabClickListener {
            override fun onClick(view: View) {
                playerPresenter?.shouldPlayOrStopSound()
            }
        })
    }

    private fun initTagView() {
        val flexLayoutManager = FlexboxLayoutManager(context)
        flexLayoutManager.flexDirection = FlexDirection.ROW
        flexLayoutManager.justifyContent = JustifyContent.FLEX_START
        tagView.layoutManager = flexLayoutManager
        soundDetailAdapter = SoundTagsAdapter(mutableListOf(), this)
        tagView.adapter = soundDetailAdapter
    }

    private fun initInfoView() {

    }

    override fun onSoundLoadedSuccess(sound: SoundDetail) {
        playerPresenter?.setMediaSource(sound.previews.previewHqMp3)
        soundTitle.text = sound.name
        soundUsername.text = "by ${sound.username}"
        soundDuration.text = "${sound.duration.round(ROUND_POSITIONS)}s"
        soundImage.setHeightRatio(DETAIL_IMG_HEIGHT.toFloat() / DETAIL_IMG_WIDTH.toFloat())
        soundImage.loadFromUrl(sound.images.waveformL)
        soundDescription.text = sound.description
        ratingView.text = "${sound.avgRating.round(ROUND_POSITIONS)}/${sound.numRatings}"
        numDownloadsView.text = "Downloaded ${sound.numDownloads} times"
        populateTagView(sound.tags)

        sound.samplerate
        sound.bitrate
        sound.bitdepth
        sound.type
        sound.channels
        sound.filesize
        sound.license
        sound.created
    }

    private fun populateTagView(tags: MutableList<String>) {
        soundDetailAdapter?.setList(tags)
    }

    override fun onSoundLoadedFailure(error: Throwable) {
        error.printStackTrace()
        showError()
        hideLoading()
    }

    override fun onSoundLoadedComplete() {
        soundDetailAdapter?.notifyDataSetChanged()
    }

    override fun showLoading() {
        loadingView?.makeVisible()
    }

    override fun hideLoading() {
        loadingView?.makeGone()
    }

    override fun showError() {
        errorView?.makeVisible()
    }

    override fun hideError() {
        errorView?.makeGone()
    }

    override fun onResume() {
        super.onResume()
        presenter?.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter?.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPlaybackEnded() {
        playPause?.playAnimation()
    }

    override fun onProgressUpdated() {
        //TODO
    }

    override fun onPlaybackError() {
        //TODO
    }

    override fun setPresenter(presenter: ISoundDetailContract.ISoundDetailPresenter) {
        this.presenter = presenter as SoundDetailPresenter
    }

    override fun setPlayerPresenter(controller: ISoundPlayerContract.SoundPlayerPresenter) {
        this.playerPresenter = controller
    }

    override fun tagClicked(tagName: String) = (activity as MainActivity).initSearchByTagFragment(tagName)

}