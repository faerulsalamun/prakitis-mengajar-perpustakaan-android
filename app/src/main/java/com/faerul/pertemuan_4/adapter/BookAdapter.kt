package com.faerul.pertemuan_4.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faerul.pertemuan_4.R
import com.faerul.pertemuan_4.UpdateBookActivity
import com.faerul.pertemuan_4.model.Book
import com.faerul.pertemuan_4.model.BookDelete
import com.faerul.pertemuan_4.retrofit.ApiService
import com.faerul.pertemuan_4.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookAdapter(private val dataList: MutableList<Book>, private val context: Context) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    private lateinit var apiService: ApiService
    private lateinit var contexts: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_book, parent, false)
        contexts = parent.context
        apiService = RetrofitClient.client!!.create(ApiService::class.java)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        Glide.with(context).load(item.image).into(holder.ivBook)
        holder.tvTitle.text = item.title
        holder.tvDescription.text = item.description
        holder.tvAuthorPublisher.text = item.author + " - " + item.publisher
        holder.tvYear.text = item.year.toString()

        holder.ibDelete.setOnClickListener {
            val call: Call<BookDelete> = apiService.deleteBook(item.id.toString())

            call.enqueue(object : Callback<BookDelete> {
                override fun onResponse(p0: Call<BookDelete>, response: Response<BookDelete>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                        dataList.removeAt(position)
                        notifyItemRemoved(position)
                    }
                }

                override fun onFailure(p0: Call<BookDelete>, p1: Throwable) {

                }

            })
        }

        holder.ibUpdate.setOnClickListener {
            val intent = Intent(context, UpdateBookActivity::class.java)
            intent.putExtra("book", item)
            (context as Activity).startActivityForResult(intent, 1)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivBook: ImageView = itemView.findViewById(R.id.iv_book)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvAuthorPublisher: TextView = itemView.findViewById(R.id.tv_author_publisher)
        val tvYear: TextView = itemView.findViewById(R.id.tv_year)
        val ibUpdate: ImageButton = itemView.findViewById(R.id.ib_update)
        val ibDelete: ImageButton = itemView.findViewById(R.id.ib_delete)
    }
}