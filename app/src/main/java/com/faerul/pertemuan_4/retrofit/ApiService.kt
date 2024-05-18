package com.faerul.pertemuan_4.retrofit

import com.faerul.pertemuan_4.model.AddBorrow
import com.faerul.pertemuan_4.model.Book
import com.faerul.pertemuan_4.model.BookDelete
import com.faerul.pertemuan_4.model.Borrow
import com.faerul.pertemuan_4.model.Report
import com.faerul.pertemuan_4.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/api/v1/books")
    fun getBooks(): Call<List<Book>>

    @POST("/api/v1/books")
    fun createBook(@Body requestModel: Book): Call<Book>

    @DELETE("/api/v1/books/{id}")
    fun deleteBook(@Path("id") id: String): Call<BookDelete>

    @PATCH("/api/v1/books/{id}")
    fun updateBook(@Path("id") id: String, @Body requestModel: Book): Call<Book>

    @GET("/api/v1/users")
    fun getUsers(): Call<List<User>>

    @GET("/api/v1/borrows")
    fun getBorrows(): Call<List<Borrow>>

    @POST("/api/v1/borrows")
    fun createBorrow(@Body requestModel: AddBorrow): Call<Borrow>

    @DELETE("/api/v1/borrows/{id}")
    fun deleteBorrow(@Path("id") id: String): Call<BookDelete>

    @PATCH("/api/v1/borrows/{id}")
    fun updateBorrow(@Path("id") id: String, @Body requestModel: AddBorrow): Call<Borrow>

    @GET("/api/v1/reports")
    fun getReports(): Call<Report>
}