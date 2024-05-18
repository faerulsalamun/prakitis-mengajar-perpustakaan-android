package com.faerul.pertemuan_4.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int?,
    val name: String,
    val createdAt: String
) : Parcelable {
    override fun toString(): String {
        return name
    }
}