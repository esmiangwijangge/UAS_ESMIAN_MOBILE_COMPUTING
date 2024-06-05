package com.example.projectesmian

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectesmian.databinding.ActivityDeskripsiBinding

class Deskripsi : AppCompatActivity() {
    private lateinit var binding: ActivityDeskripsiBinding

    override fun onCreate(savedInstanceState: Bundle?) {

       super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_deskripsi)
        binding = ActivityDeskripsiBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intess = intent
        var asalT = intess.getStringExtra("ASALT")
        var deskT = intess.getStringExtra("DESKT")

        var imgT = intess.getStringExtra("IMGT")
        var jdlT = intess.getStringExtra("JUDULT")


        binding.myGambar.loadImage(imgT)
        binding.namaclub.text =jdlT
        binding.asalclub.text =asalT
        binding.deskripsi.text =deskT
    }
}