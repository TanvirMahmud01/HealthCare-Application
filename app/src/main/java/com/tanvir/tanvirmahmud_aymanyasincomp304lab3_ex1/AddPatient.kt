package com.tanvir.tanvirmahmud_aymanyasincomp304lab3_ex1

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase

class AddPatient : AppCompatActivity() {
    private lateinit var editTextNurseId: TextView
    private lateinit var editTextPatientId: EditText
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextDepartment: EditText
    private lateinit var editTextRoom: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

//        editTextNurseId = findViewById(R.id.editTextNurseId)

        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextDepartment = findViewById(R.id.editTextDepartment)
        editTextRoom = findViewById(R.id.editTextRoom)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        // Get nurseId from Shared Preferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val nurseId = sharedPreferences.getString("nurseId", "")
        // Set nurseId in EditText
//        editTextNurseId.setText(nurseId)

        buttonSubmit.setOnClickListener {

            val firstName = editTextFirstName.text.toString()
            val lastName = editTextLastName.text.toString()
            val department = editTextDepartment.text.toString()
            val room = editTextRoom.text.toString()
//            val nurseId = editTextNurseId.text.toString()

            val databaseReference = FirebaseDatabase.getInstance().getReference("patients")
            val patientId = databaseReference.push().key // Generates a unique ID for the patient

            val patient = Patient(patientId!!, firstName, lastName, department, nurseId, room)
            // Save patient to Firebase
            val database = FirebaseDatabase.getInstance()
            val listRootRef = database!!.getReference("Patients")
            val itemRootRef = listRootRef!!.child(patient!!.patientId.toString())
            itemRootRef.child("patientId").setValue(patient!!.patientId)
            itemRootRef.child("firstName").setValue(patient!!.firstName)
            itemRootRef.child("lastName").setValue(patient!!.lastName)
            itemRootRef.child("department").setValue(patient!!.department)
            itemRootRef.child("nurseId").setValue(patient!!.nurseId)
            itemRootRef.child("room").setValue(patient!!.room)

            val intent = Intent(this, ListPatients::class.java)
            startActivity(intent)
        }
    }

}