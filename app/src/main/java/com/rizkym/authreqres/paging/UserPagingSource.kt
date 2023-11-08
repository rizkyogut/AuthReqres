package com.rizkym.authreqres.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rizkym.authreqres.remote.config.ApiConfig
import com.rizkym.authreqres.remote.response.DataItem

class UserPagingSource() : PagingSource<Int, DataItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = ApiConfig.getApiService()
                .getUsersPages(page, params.loadSize)

            LoadResult.Page(
                data = responseData.data,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.data.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}