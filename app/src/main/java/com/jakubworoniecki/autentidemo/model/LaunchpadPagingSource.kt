package com.jakubworoniecki.autentidemo.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jakubworoniecki.autentidemo.api.AutentiApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_INDEX = 1

class LaunchpadPagingSource(private val api: AutentiApi) : PagingSource<Int, LaunchpadItem>() {
    override fun getRefreshKey(state: PagingState<Int, LaunchpadItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LaunchpadItem> {
        return try {
            val position = params.key ?: STARTING_INDEX
            val response = api.getAllLaunchpads()
            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}