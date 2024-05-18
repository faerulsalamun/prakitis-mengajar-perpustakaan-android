package com.faerul.pertemuan_4

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.faerul.pertemuan_4.adapter.BorrowAdapter
import com.faerul.pertemuan_4.databinding.ActivityBorrowBinding
import com.faerul.pertemuan_4.model.Book
import com.faerul.pertemuan_4.model.Borrow
import com.faerul.pertemuan_4.retrofit.ApiService
import com.faerul.pertemuan_4.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BorrowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBorrowBinding
    lateinit var dataBorrow: MutableList<Borrow>
    lateinit var adapter: BorrowAdapter
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBorrowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.client!!.create(ApiService::class.java)
        dataBorrow = mutableListOf()

        adapter = BorrowAdapter(dataBorrow, this)
        binding.rvBorrow.adapter = adapter
        binding.rvBorrow.layoutManager = LinearLayoutManager(this)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddBorrowActivity::class.java)
            startActivityForResult(intent, 1)
        }

        loadData()
    }

    private fun loadData() {
        val call: Call<List<Borrow>> = apiService.getBorrows()

        call.enqueue(object : Callback<List<Borrow>> {
            override fun onResponse(p0: Call<List<Borrow>>, response: Response<List<Borrow>>) {
                if (response.isSuccessful) {
                    dataBorrow.clear()

                    response.body()?.let { dataBorrow.addAll(it) }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(p0: Call<List<Borrow>>, p1: Throwable) {
            }
        })
    }
}