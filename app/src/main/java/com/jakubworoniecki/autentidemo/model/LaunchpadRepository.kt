package com.jakubworoniecki.autentidemo.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.jakubworoniecki.autentidemo.api.AutentiApi
import javax.inject.Inject
import javax.inject.Singleton
private const val PAGE_SIZE = 10
private const val MAX_SIZE = 50
@Singleton
class LaunchpadRepository @Inject constructor(private val autentiApi: AutentiApi) {
    fun getLaunchpads() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            maxSize = MAX_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { LaunchpadPagingSource(autentiApi) }
    ).liveData
}