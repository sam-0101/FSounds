package com.samuelepontremoli.fsounds.ui.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.samuelepontremoli.fsounds.R
import com.samuelepontremoli.fsounds.network.SoundPartialDetail
import com.samuelepontremoli.fsounds.ui.MainActivity
import com.samuelepontremoli.fsounds.utils.makeGone
import com.samuelepontremoli.fsounds.utils.makeVisible
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_loading.*

/**
 * FSounds - Created by s.pontremoli on 24/07/2017.
 */
class SoundSearchFragment : Fragment(), ISoundSearchContract.ISoundSearchView, SoundSearchAdapter.SoundItemClickListener {

    private var presenter: SoundSearchPresenter? = null

    private var soundSearchAdapter: SoundSearchAdapter? = null

    companion object {
        val TAG = "SoundSearchFragment"
        fun newInstance(): SoundSearchFragment {
            return SoundSearchFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        errorView.makeGone()
        loadingView.makeGone()
        searchRecycler.isDrawingCacheEnabled = true
        searchRecycler.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        searchRecycler.layoutManager = manager
        soundSearchAdapter = SoundSearchAdapter(mutableListOf(), this)
        searchRecycler.adapter = soundSearchAdapter
    }

    override fun setPresenter(presenter: ISoundSearchContract.ISoundSearchPresenter) {
        this.presenter = presenter as SoundSearchPresenter
    }

    override fun onSoundsLoadedSuccess(list: List<SoundPartialDetail>) {
        soundSearchAdapter?.setList(list as MutableList<SoundPartialDetail>)
    }

    override fun onSoundsLoadedFailure(error: Throwable) {
        error.printStackTrace()
        showError()
        hideLoading()
    }

    override fun onSoundsLoadedComplete() {
        searchRecycler.adapter.notifyDataSetChanged()
    }

    override fun emptySounds() {
        soundSearchAdapter?.clearItems()
    }

    override fun showLoading() {
        loadingView.makeVisible()
    }

    override fun hideLoading() {
        loadingView.makeGone()
    }

    override fun showError() {
        errorView.makeVisible()
    }

    override fun hideError() {
        errorView.makeGone()
    }

    override fun onResume() {
        super.onResume()
        presenter?.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter?.unsubscribe()
    }

    override fun soundClicked(soundId: Int) = (activity as MainActivity).initDetailFragment(soundId)

}
