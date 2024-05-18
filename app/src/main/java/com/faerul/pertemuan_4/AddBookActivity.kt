package com.faerul.pertemuan_4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.faerul.pertemuan_4.databinding.ActivityAddBookBinding
import com.faerul.pertemuan_4.model.Book
import com.faerul.pertemuan_4.retrofit.ApiService
import com.faerul.pertemuan_4.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val apiService = RetrofitClient.client!!.create(ApiService::class.java)

            val call: Call<Book> = apiService.createBook(
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
            Log.d("gagal karena","masuk")

            call.enqueue(object : Callback<Book> {
                override fun onResponse(p0: Call<Book>, response: Response<Book>) {
                    Toast.makeText(baseContext, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                }

                override fun onFailure(p0: Call<Book>, response: Throwable) {
                    Log.d("gagal karena",response.toString())
                }

            })
        }

    }
}