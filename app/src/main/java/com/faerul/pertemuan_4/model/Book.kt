package com.faerul.pertemuan_4.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Int?,
    val image: String,
    val title: String,
    val description: String,
    val author: String,
    val publisher: String,
    val year: Int
) : Parcelable {

    override fun toString(): String {
        return title
    }
}