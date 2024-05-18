package com.faerul.pertemuan_4

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.faerul.pertemuan_4.databinding.ActivityReportBinding
import com.faerul.pertemuan_4.model.BookReport
import com.faerul.pertemuan_4.model.BorrowReport
import com.faerul.pertemuan_4.model.Report
import com.faerul.pertemuan_4.retrofit.ApiService
import com.faerul.pertemuan_4.retrofit.RetrofitClient
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.client!!.create(ApiService::class.java)

        loadData()
    }

    private fun loadData() {
        val call: Call<Report> = apiService.getReports()

        call.enqueue(object : Callback<Report> {
            override fun onResponse(p0: Call<Report>, response: Response<Report>) {
                if (response.isSuccessful) {
                    response.body()?.books?.let { loadBuku(it) }
                    response.body()?.borrow?.let { loadBarChart(it) }
                }
            }

            override fun onFailure(p0: Call<Report>, p1: Throwable) {
            }
        })
    }

    private fun loadBuku(bookReport: List<BookReport>) {
        val entries = ArrayList<PieEntry>()

        for (position in 0 until bookReport.size) {
            entries.add(
                PieEntry(
                    bookReport[position].total.toFloat(),
                    bookReport[position].year.toString(),
                )
            )
        }

        // Konfigurasi set data
        val dataSet = PieDataSet(entries, "Data Buku")
        dataSet.colors = listOf(Color.RED, Color.GREEN, Color.BLUE)

        // Konfigurasi data
        val pieData = PieData(dataSet)
        pieData.setValueTextSize(15f)
        binding.pieChart.data = pieData

        binding.pieChart.description.text = "Data Buku Per Tahun"

        binding.pieChart.invalidate()
    }

    private fun loadBarChart(borrowReport: BorrowReport) {
        val entries = ArrayList<BarEntry>()

        entries.add(BarEntry(0f, borrowReport.januari.toFloat()))
        entries.add(BarEntry(1f, borrowReport.februari.toFloat()))
        entries.add(BarEntry(2f, borrowReport.maret.toFloat()))
        entries.add(BarEntry(3f, borrowReport.april.toFloat()))
        entries.add(BarEntry(4f, borrowReport.mei.toFloat()))
        entries.add(BarEntry(5f, borrowReport.juni.toFloat()))
        entries.add(BarEntry(6f, borrowReport.juli.toFloat()))
        entries.add(BarEntry(7f, borrowReport.agustus.toFloat()))
        entries.add(BarEntry(8f, borrowReport.september.toFloat()))
        entries.add(BarEntry(9f, borrowReport.oktober.toFloat()))
        entries.add(BarEntry(10f, borrowReport.november.toFloat()))
        entries.add(BarEntry(11f, borrowReport.desember.toFloat()))

        val months = arrayOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "Mei",
            "Jun",
            "Jul",
            "Agu",
            "Sep",
            "Okt",
            "Nov",
            "Des",
        )

        // Konfigurasi set data
        val dataSet = BarDataSet(entries, "Monthly Data")
        dataSet.color = Color.BLUE

        // Konfigurasi data
        val barData = BarData(dataSet)
        barData.setValueTextSize(10f)
        binding.barChart.data = barData

        binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(months)

        binding.barChart.description.text = "Monthly Data"

        binding.barChart.invalidate()
    }
}