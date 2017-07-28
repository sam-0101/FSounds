package com.samuelepontremoli.fsounds.network

/**
 * FSounds - Created by s.pontremoli on 24/07/2017.
 */
object FreeSoundRepositoryProvider {

    fun provideFreeSoundRepository(): FreeSoundRepository {
        return FreeSoundRepository(FreeSoundService.Factory.create())
    }

}