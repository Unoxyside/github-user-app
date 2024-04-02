package com.bahasyim.mygithubapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favoriteTable")
@Parcelize
class FavEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "avatar")
    var avatarUrl: String? = null
) : Parcelable