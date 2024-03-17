package com.tanvir.tanvirmahmud_aymanyasincomp304lab3_ex1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var editTextNurseId: EditText
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextDepartment: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSubmit: Button

    private var database: FirebaseDatabase? = null
    private var listRootRef: DatabaseReference? = null
    var nurse: Nurse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextNurseId = findViewById(R.id.editTextNurseId)
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextDepartment = findViewById(R.id.editTextDepartment)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance()
        listRootRef = database!!.getReference("Nurses")

        buttonSubmit.setOnClickListener {

            val nurseId = editTextNurseId.text.toString()
            val firstName = editTextFirstName.text.toString()
            val lastName = editTextLastName.text.toString()
            val department = editTextDepartment.text.toString()
            val password = editTextPassword.text.toString()

            nurse = Nurse(
                nurseId, firstName, lastName, department, password
            )
            Toast.makeText(
                applicationContext,
                nurse.toString(),
                Toast.LENGTH_SHORT
            ).show()

            // Create a unique child under "Nurses" node for this nurse.
            val itemRootRef = listRootRef!!.child(nurse!!.nurseId.toString())
            // Set the values in Firebase for this nurse.
            itemRootRef.child("nurseId").setValue(nurse!!.nurseId)
            itemRootRef.child("firstName").setValue(nurse!!.firstName)
            itemRootRef.child("lastName").setValue(nurse!!.lastName)
            itemRootRef.child("department").setValue(nurse!!.department)
            itemRootRef.child("password").setValue(nurse!!.password)

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)


            // Now you can use these values as needed, such as sending them to a server or storing them locally.
            // Example:
            // sendDataToServer(nurseId, firstName, lastName, department, password)
        }
    }
}