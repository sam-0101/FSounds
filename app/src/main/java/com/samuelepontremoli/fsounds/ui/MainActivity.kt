package com.samuelepontremoli.fsounds.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.samuelepontremoli.fsounds.R
import com.samuelepontremoli.fsounds.commons.BaseActivity
import com.samuelepontremoli.fsounds.commons.BasePresenter
import com.samuelepontremoli.fsounds.ui.detail.SoundDetailFragment
import com.samuelepontremoli.fsounds.ui.detail.SoundDetailPresenter
import com.samuelepontremoli.fsounds.ui.search.SoundSearchFragment
import com.samuelepontremoli.fsounds.ui.search.SoundSearchPresenter

/**
 * FSounds - Created by s.pontremoli on 24/07/2017.
 */
class MainActivity : BaseActivity() {

    private var presenter: BasePresenter? = null

    private val toolbar: Toolbar by lazy { findViewById(R.id.mainToolbar) as Toolbar }

    private lateinit var toolbarMenu: Menu

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
            presenter = SoundSearchPresenter(searchFragment)
        }
        changeFragment(searchFragment, true, SoundSearchFragment.TAG)
    }

    fun initDetailFragment(soundId: Int) {
        val detailFragment = SoundDetailFragment.newInstance()
        presenter = SoundDetailPresenter(detailFragment, soundId)
        changeFragment(detailFragment, false, SoundDetailFragment.TAG)
        showBackButton()
        hideSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_main_toolbar, menu)
        toolbarMenu = menu
        initSearch(menu)
        return true
    }

    private fun initSearch(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.background = ContextCompat.getDrawable(this, R.drawable.side_nav_bar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val searchFragment = SoundSearchFragment.newInstance()
                if (query != null) {
                    presenter = SoundSearchPresenter(searchFragment, query)
                }
                changeFragment(searchFragment, true, SoundSearchFragment.TAG)
                hideKeyboard()
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
        item.setVisible(false)
    }

    private fun showSearch() {
        val item = toolbarMenu.findItem(R.id.search)
        item.setVisible(true)
    }

}
