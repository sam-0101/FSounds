package com.samuelepontremoli.fsounds.network

import com.samuelepontremoli.fsounds.utils.FREE_SOUND_KEY
import io.reactivex.Flowable
import retrofit2.Call

/**
 * FSounds - Created by s.pontremoli on 24/07/2017.
 */
class FreeSoundRepository(val apiService: FreeSoundService) {

    fun getSearchResults(query: String): Flowable<SearchResult> {
        return apiService.getTextSearch(query, FREE_SOUND_KEY)
    }

    fun getSoundDetail(id: Int): Flowable<SoundDetail> {
        return apiService.getSoundDetail(id, FREE_SOUND_KEY)
    }

}