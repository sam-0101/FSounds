package com.samuelepontremoli.fsounds.ui.search

import com.samuelepontremoli.fsounds.network.FreeSoundRepositoryProvider
import com.samuelepontremoli.fsounds.network.SoundPartialDetail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * FSounds - Created by s.pontremoli on 24/07/2017.
 */
class SoundSearchPresenter(val view: ISoundSearchContract.ISoundSearchView, val query: String) : ISoundSearchContract.ISoundSearchPresenter {

    private val subscriptions: CompositeDisposable

    var mode = MODE_TEXT_SEARCH

    companion object {
        val MODE_TEXT_SEARCH = 1
        val MODE_TAG_SEARCH = 2
    }

    init {
        subscriptions = CompositeDisposable()
        view.setPresenter(this)
    }

    override fun subscribe() {
        view.showLoading()
        when(mode) {
            MODE_TEXT_SEARCH -> loadSoundsFromText()
            MODE_TAG_SEARCH -> loadSoundsFromTag()
        }
    }

    override fun loadSoundsFromText() {
        //Set api repository
        val freeSoundRepository = FreeSoundRepositoryProvider.provideFreeSoundRepository()

        val freeSoundFlow = freeSoundRepository.getSearchResults(query)
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

    override fun loadSoundsFromTag() {
        val freeSoundRepository = FreeSoundRepositoryProvider.provideFreeSoundRepository()

        val freeSoundFlow = freeSoundRepository.getTextFilteredSearchResults("tag:$query")
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
