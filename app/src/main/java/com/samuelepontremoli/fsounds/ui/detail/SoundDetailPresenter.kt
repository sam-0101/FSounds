package com.samuelepontremoli.fsounds.ui.detail

import com.samuelepontremoli.fsounds.network.FreeSoundRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * FSounds - Created by s.pontremoli on 26/07/2017.
 */
class SoundDetailPresenter(val view: ISoundDetailContract.ISoundDetailView, val id: Int) : ISoundDetailContract.ISoundDetailPresenter {

    private val TAG = "TrendingPresenter"

    private val subscriptions: CompositeDisposable

    private var playBackUrl: String = ""

    init {
        subscriptions = CompositeDisposable()
        view.setPresenter(this)
    }

    override fun subscribe() {
        view.showLoading()
        loadSound()
    }

    override fun loadSound() {
        //Set api repository
        val freeSoundRepository = FreeSoundRepositoryProvider.provideFreeSoundRepository()

        val freeSoundFlow = freeSoundRepository.getSoundDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data ->
                    view.onSoundLoadedSuccess(data)
                    playBackUrl = data.url
                }, {
                    error ->
                    view.onSoundLoadedFailure(error)
                }, {
                    view.onSoundLoadedComplete()
                    view.hideLoading()
                    view.hideError()
                })

        subscriptions.add(freeSoundFlow)
    }

    override fun shouldPlayOrStopSound() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

}