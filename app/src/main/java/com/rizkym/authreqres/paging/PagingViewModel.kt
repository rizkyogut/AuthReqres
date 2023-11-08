package com.rizkym.authreqres.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.rizkym.authreqres.remote.response.DataItem

class PagingViewModel : ViewModel() {

    fun getPagingUsers(): LiveData<PagingData<DataItem>> =
        Pager(
            config = PagingConfig(
                pageSize = 6
            ),
            pagingSourceFactory = {
                UserPagingSource()
            }
        ).liveData.cachedIn(viewModelScope)

    class ViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PagingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PagingViewModel() as T
            } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}