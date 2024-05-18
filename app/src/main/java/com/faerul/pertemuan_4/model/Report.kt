package com.faerul.pertemuan_4.model

data class Report(
    val borrow: BorrowReport,
    val books: List<BookReport>
)

data class BorrowReport(
    val januari: String,
    val februari: String,
    val maret: String,
    val april: String,
    val mei: String,
    val juni: String,
    val juli: String,
    val agustus: String,
    val september: String,
    val oktober: String,
    val november: String,
    val desember: String,
)

data class BookReport(
    val total: Int,
    val year: Int
)