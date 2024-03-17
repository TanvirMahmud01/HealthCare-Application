package com.tanvir.tanvirmahmud_aymanyasincomp304lab3_ex1


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        databaseReference = FirebaseDatabase.getInstance().reference.child("Nurses")

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Query Firebase to get nurse data by nurse ID
            val query: Query = databaseReference.orderByChild("nurseId").equalTo(username)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            val nurse = snapshot.getValue(Nurse::class.java)

                            if (nurse != null && nurse.password == password) {
                                // Nurse found and password matches, login successful
                                Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()


                                // Store nurseId in Shared Preferences
                                val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("nurseId", nurse.nurseId)
                                editor.apply()

                                // Start a new activity
                                val intent = Intent(this@LoginActivity, ListPatients::class.java)
                                startActivity(intent)
                                return
                            }
                        }
                        // Nurse found but password doesn't match
                        Toast.makeText(this@LoginActivity, "Invalid Password", Toast.LENGTH_SHORT).show()
                    } else {
                        // Nurse not found
                        Toast.makeText(this@LoginActivity, "Nurse not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle error
                    Toast.makeText(this@LoginActivity, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
