package com.tanvir.tanvirmahmud_aymanyasincomp304lab3_ex1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ListPatients : AppCompatActivity() {
    private lateinit var btnAddPatient: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_patients)

        btnAddPatient = findViewById(R.id.btnAddPatient)
        btnAddPatient.setOnClickListener {
            val intent = Intent(this, AddPatient::class.java)
            startActivity(intent)
        }
    }
}