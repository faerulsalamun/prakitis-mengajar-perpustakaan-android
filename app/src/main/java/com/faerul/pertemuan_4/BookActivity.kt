package com.faerul.pertemuan_4

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.faerul.pertemuan_4.adapter.BookAdapter
import com.faerul.pertemuan_4.databinding.ActivityBookBinding
import com.faerul.pertemuan_4.model.Book
import com.faerul.pertemuan_4.retrofit.ApiService
import com.faerul.pertemuan_4.retrofit.RetrofitClient.client
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener  {

    private lateinit var binding : ActivityBookBinding
    lateinit var dataBooks : MutableList<Book>
    lateinit var adapter: BookAdapter
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = client!!.create(ApiService::class.java)

        dataBooks = mutableListOf()

        adapter = BookAdapter(dataBooks,this)
        binding.rvBook.adapter = adapter
        binding.rvBook.layoutManager = GridLayoutManager(this,2)

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddBookActivity::class.java)
            startActivityForResult(intent, 1)
        }

        binding.swipeLayout.setOnRefreshListener(this)
        loadData()
    }

    private fun loadData(){
        val call : Call<List<Book>> = apiService.getBooks()

        binding.swipeLayout.isRefreshing = true

        call.enqueue(object : Callback<List<Book>>{
            override fun onResponse(p0: Call<List<Book>>, response: Response<List<Book>>) {
                if(response.isSuccessful){
                    binding.swipeLayout.isRefreshing = false

                    dataBooks.clear()
                    response.body()?.let { dataBooks.addAll(it) }
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(p0: Call<List<Book>>, p1: Throwable) {
                binding.swipeLayout.isRefreshing = false
            }

        })
    }

    override fun onRefresh() {
        loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === 1) {
            if (resultCode === RESULT_OK) {
                loadData()
            }
        }
    }
}