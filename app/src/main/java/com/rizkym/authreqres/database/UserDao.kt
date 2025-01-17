package com.rizkym.authreqres.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rizkym.authreqres.network.response.DataItem

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetUser(user: List<DataItem>)

    @Query("SELECT * FROM user")
    fun getAllUser() : PagingSource<Int, DataItem>

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}