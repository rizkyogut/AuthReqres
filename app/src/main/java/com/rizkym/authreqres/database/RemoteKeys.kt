package com.rizkym.authreqres.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remote_keys")
data class RemoteKeys (
    @PrimaryKey
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)