package com.rizkym.authreqres.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rizkym.authreqres.database.UserDatabase
import com.rizkym.authreqres.network.config.ApiService
import com.rizkym.authreqres.network.response.DataItem

class UserRepository(private val userDatabase: UserDatabase, private val apiService: ApiService) {
    fun getUser(): LiveData<PagingData<DataItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = UserRemoteMediator(userDatabase, apiService),
            pagingSourceFactory = {
//                UserPagingSource(apiService)
                userDatabase.userDao().getAllUser()
            }
        ).liveData
    }
}