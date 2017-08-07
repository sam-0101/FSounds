package com.samuelepontremoli.fsounds.ui

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.samuelepontremoli.fsounds.R
import com.samuelepontremoli.fsounds.commons.BaseActivity
import com.samuelepontremoli.fsounds.commons.BasePresenter
import com.samuelepontremoli.fsounds.player.SoundPlayer
import com.samuelepontremoli.fsounds.ui.detail.SoundDetailFragment
import com.samuelepontremoli.fsounds.ui.detail.SoundDetailPresenter
import com.samuelepontremoli.fsounds.ui.search.SoundSearchFragment
import com.samuelepontremoli.fsounds.ui.search.SoundSearchPresenter

/**
 * FSounds - Created by s.pontremoli on 24/07/2017.
 */
class MainActivity : BaseActivity() {

    private val toolbar: Toolbar by lazy { findViewById(R.id.mainToolbar) as Toolbar }

    private lateinit var toolbarMenu: Menu

    private var presenter: BasePresenter? = null

    private var soundPlayer: SoundPlayer? = null

    companion object {
        val BACK_BTN_THRESHOLD = 2
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        initDefaultFragment()
    }

    private fun initDefaultFragment() {
        val searchFragment = SoundSearchFragment.newInstance()
        if (presenter == null) {
            presenter = SoundSearchPresenter(searchFragment, "amen")
        }
        changeFragment(searchFragment, true, SoundSearchFragment.TAG)
    }

    fun initDetailFragment(soundId: Int) {
        val detailFragment = SoundDetailFragment.newInstance()
        presenter = SoundDetailPresenter(detailFragment, soundId)
        soundPlayer = SoundPlayer(detailFragment, baseContext)
        changeFragment(detailFragment, false, SoundDetailFragment.TAG)
        showBackButton()
        hideSearch()
    }

    fun initTextSearchFragment(query: String?) {
        val searchFragment = SoundSearchFragment.newInstance()
        if (query != null) {
            presenter = SoundSearchPresenter(searchFragment, query)
        }
        (presenter as SoundSearchPresenter).mode = SoundSearchPresenter.MODE_TEXT_SEARCH
        changeFragment(searchFragment, false, SoundSearchFragment.TAG)
        hideKeyboard()
        hideSearchView()
    }

    fun initSearchByTagFragment(tagName: String) {
        val searchFragment = SoundSearchFragment.newInstance()
        presenter = SoundSearchPresenter(searchFragment, tagName)
        (presenter as SoundSearchPresenter).mode = SoundSearchPresenter.MODE_TAG_SEARCH
        changeFragment(searchFragment, false, SoundSearchFragment.TAG)
        showBackButton()
        showSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_main_toolbar, menu)
        toolbarMenu = menu
        initSearch(menu)
        return true
    }

    private fun initSearch(menu: Menu) {
        val searchView = menu.findItem(R.id.search).actionView as SearchView
//        searchView.background = ContextCompat.getDrawable(this, R.drawable.side_nav_bar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                initTextSearchFragment(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                android.R.id.home -> onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        soundPlayer?.stopPlayback()
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount == BACK_BTN_THRESHOLD) {
            hideBackButton()
            showSearch()
        }
        super.onBackPressed()
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun hideBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun hideSearch() {
        val item = toolbarMenu.findItem(R.id.search)
        item.collapseActionView()
        item.setVisible(false)
    }

    private fun hideSearchView() {
        val item = toolbarMenu.findItem(R.id.search)
        item.collapseActionView()
    }

    private fun showSearch() {
        val item = toolbarMenu.findItem(R.id.search)
        item.setVisible(true)
    }

}
