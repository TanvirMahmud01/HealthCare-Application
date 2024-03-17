package com.tanvir.tanvirmahmud_aymanyasincomp304lab3_ex1

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListPatients : AppCompatActivity() {
    private lateinit var btnAddPatient: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var patientAdapter: PatientAdapter
    private val patientList = ArrayList<Patient>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_patients)

        btnAddPatient = findViewById(R.id.btnAddPatient)
        btnAddPatient.setOnClickListener {
            val intent = Intent(this, AddPatient::class.java)
            startActivity(intent)
        }


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        patientAdapter = PatientAdapter(patientList)
        recyclerView.adapter = patientAdapter

        val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val nurseId = sharedPreferences.getString("nurseId", "")




        val databaseReference = FirebaseDatabase.getInstance().getReference("Patients")
        databaseReference.orderByChild("nurseId").equalTo(nurseId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                patientList.clear()
                for (patientSnapshot in dataSnapshot.children) {
                    val patient = patientSnapshot.getValue(Patient::class.java)
                    if (patient != null) {
                        patientList.add(patient)
                    }
                }
                patientAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@ListPatients, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}