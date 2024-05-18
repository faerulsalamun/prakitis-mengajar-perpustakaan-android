package com.faerul.pertemuan_4

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.faerul.pertemuan_4.databinding.ActivityAddBorrowBinding
import com.faerul.pertemuan_4.model.AddBorrow
import com.faerul.pertemuan_4.model.Book
import com.faerul.pertemuan_4.model.Borrow
import com.faerul.pertemuan_4.model.User
import com.faerul.pertemuan_4.retrofit.ApiService
import com.faerul.pertemuan_4.retrofit.RetrofitClient
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class AddBorrowActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityAddBorrowBinding
    private lateinit var apiService: ApiService
    private lateinit var dataUsers: MutableList<User>
    private lateinit var dataBooks: MutableList<Book>
    private var mDateFrom: Int = 0
    private var userId: Int = 0
    private var bookId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBorrowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.client!!.create(ApiService::class.java)

        dataUsers = mutableListOf()
        dataBooks = mutableListOf()

        loadData()

        binding.spUser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedUser = parent!!.getItemAtPosition(position) as User

                userId = selectedUser.id!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.spBook.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedBook = parent!!.getItemAtPosition(position) as Book

                bookId = selectedBook.id!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.etStartDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                showCalender(0)
            }
        }

        binding.etEndDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                showCalender(1)
            }
        }

        binding.btnSubmit.setOnClickListener {
            val call: Call<Borrow> = apiService.createBorrow(
                AddBorrow(
                    bookId,
                    userId,
                    "belum dikembalikan",
                    binding.etStartDate.text.toString(),
                    binding.etEndDate.text.toString(),
                )
            )

            call.enqueue(object : Callback<Borrow> {
                override fun onResponse(p0: Call<Borrow>, p1: Response<Borrow>) {
                    Toast.makeText(baseContext, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                }

                override fun onFailure(p0: Call<Borrow>, p1: Throwable) {

                }
            })
        }
    }

    private fun loadData(){
        // Mengambil data user untuk dapat ditampilkan ke spinner user
        val callUser: Call<List<User>> = apiService.getUsers()

        callUser.enqueue(object : Callback<List<User>> {
            override fun onResponse(p0: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    dataUsers.clear()

                    response.body()?.map { dataUsers.add(it)}

                    val adapter = ArrayAdapter(baseContext,R.layout.simple_spinner_item,dataUsers)

                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

                    binding.spUser.adapter = adapter
                }
            }

            override fun onFailure(p0: Call<List<User>>, p1: Throwable) {
            }
        })

        // Mengambil data buku untuk dapat ditampilkan ke spinner buku
        val callBook: Call<List<Book>> = apiService.getBooks()

        callBook.enqueue(object : Callback<List<Book>> {
            override fun onResponse(p0: Call<List<Book>>, response: Response<List<Book>>) {
                if(response.isSuccessful){
                    dataBooks.clear()

                    response.body()?.map { dataBooks.add(it)}

                    val adapter = ArrayAdapter(baseContext,R.layout.simple_spinner_item,dataBooks)

                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

                    binding.spBook.adapter = adapter
                }
            }

            override fun onFailure(p0: Call<List<Book>>, p1: Throwable) {
            }
        })
    }

    private fun showCalender(from: Int) {
        mDateFrom = from
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
            this@AddBorrowActivity,now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH)
        )
        dpd.version = DatePickerDialog.Version.VERSION_1
        dpd.setAccentColor("#3F51B5")
        dpd.show(supportFragmentManager,"date")
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val newDay = if(dayOfMonth < 10) "0" + dayOfMonth else dayOfMonth.toString() + ""
        val month = monthOfYear + 1

        val date = "$newDay-$month-$year"

        when(mDateFrom){
            0 -> binding.etStartDate.setText(date)
            1 -> binding.etEndDate.setText(date)
            else -> {

            }
        }
    }
}