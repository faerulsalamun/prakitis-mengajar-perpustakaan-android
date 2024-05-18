package com.faerul.pertemuan_4.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faerul.pertemuan_4.R
import com.faerul.pertemuan_4.model.Borrow
import com.faerul.pertemuan_4.retrofit.ApiService
import com.faerul.pertemuan_4.retrofit.RetrofitClient

class BorrowAdapter(private val dataList: MutableList<Borrow>, private val context: Context) :
    RecyclerView.Adapter<BorrowAdapter.ViewHolder>() {

    private lateinit var apiService: ApiService
    private lateinit var contexts: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BorrowAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_borrow, parent, false)
        contexts = parent.context

        apiService = RetrofitClient.client!!.create(ApiService::class.java)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: BorrowAdapter.ViewHolder, position: Int) {
        val item = dataList[position]
        holder.tvNama.text = item.user?.name
        holder.tvBuku.text = item.book?.title
        holder.tvTanggalPinjam.text = item.startDate
        holder.tvTanggalPengembalian.text = item.endDate
        holder.tvStatus.text = item.status
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tv_name)
        val tvBuku: TextView = itemView.findViewById(R.id.tv_book)
        val tvTanggalPinjam: TextView = itemView.findViewById(R.id.tv_start_date)
        val tvTanggalPengembalian: TextView = itemView.findViewById(R.id.tv_end_date)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
    }
}