package com.faerul.pertemuan_4

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.faerul.pertemuan_4.databinding.ActivityUpdateBookBinding
import com.faerul.pertemuan_4.model.Book
import com.faerul.pertemuan_4.retrofit.ApiService
import com.faerul.pertemuan_4.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val book: Book = intent.getParcelableExtra("book")!!

        if (book != null) {
            binding.etImage.text = Editable.Factory.getInstance().newEditable(book.image)
            binding.etTitle.text = Editable.Factory.getInstance().newEditable(book.title)
            binding.etDescription.text =
                Editable.Factory.getInstance().newEditable(book.description)
            binding.etAuthor.text = Editable.Factory.getInstance().newEditable(book.author)
            binding.etPublisher.text = Editable.Factory.getInstance().newEditable(book.publisher)
            binding.etYear.text = Editable.Factory.getInstance().newEditable(book.year.toString())
        }

        binding.btnSubmit.setOnClickListener {
            val apiService = RetrofitClient.client!!.create(ApiService::class.java)

            val call: Call<Book> = apiService.updateBook(
                book.id.toString(),
                Book(
                    null,
                    binding.etImage.text.toString(),
                    binding.etTitle.text.toString(),
                    binding.etDescription.text.toString(),
                    binding.etAuthor.text.toString(),
                    binding.etPublisher.text.toString(),
                    binding.etYear.text.toString().toInt(),
                )
            )

            call.enqueue(object : Callback<Book> {
                override fun onResponse(p0: Call<Book>, response: Response<Book>) {
                    if (response.isSuccessful) {
                        Toast.makeText(baseContext, "Data berhasil disimpan", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent()
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }

                override fun onFailure(p0: Call<Book>, p1: Throwable) {
                }

            })
        }

    }
}