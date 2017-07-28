package com.samuelepontremoli.fsounds.ui.search

import com.samuelepontremoli.fsounds.network.FreeSoundRepositoryProvider
import com.samuelepontremoli.fsounds.network.SoundPartialDetail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * FSounds - Created by s.pontremoli on 24/07/2017.
 */
class SoundSearchPresenter(val view: ISoundSearchContract.ISoundSearchView) : ISoundSearchContract.ISoundSearchPresenter {

    private val subscriptions: CompositeDisposable

    init {
        subscriptions = CompositeDisposable()
        view.setPresenter(this)
    }

    override fun subscribe() {
        view.showLoading()
        loadSounds()
    }

    override fun loadSounds() {
        //Set api repository
        val freeSoundRepository = FreeSoundRepositoryProvider.provideFreeSoundRepository()

        val freeSoundFlow = freeSoundRepository.getSearchResults("amen")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data ->
                    view.onSoundsLoadedSuccess(data.results)
                }, {
                    error ->
                    view.onSoundsLoadedFailure(error)
                }, {
                    view.onSoundsLoadedComplete()
                    view.hideLoading()
                    view.hideError()
                })

        subscriptions.add(freeSoundFlow)
    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

}
