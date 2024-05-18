package com.faerul.pertemuan_4.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Borrow(
    val id: Int?,
    val bookId: Int?,
    val userId: Int?,
    val status: String,
    val startDate: String,
    val endDate: String,
    val book: Book,
    val user: User
) : Parcelable