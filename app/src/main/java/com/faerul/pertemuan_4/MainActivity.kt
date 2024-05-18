package com.faerul.pertemuan_4

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.faerul.pertemuan_4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvBook.setOnClickListener {
            val intent = Intent(this, BookActivity::class.java)
            startActivity(intent)
        }

        binding.cvReport.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }

        binding.cvLoan.setOnClickListener {
            val intent = Intent(this, BorrowActivity::class.java)
            startActivity(intent)
        }

    }
}