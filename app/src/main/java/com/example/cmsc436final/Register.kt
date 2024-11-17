package com.example.cmsc436final

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {
    private lateinit var submitButton: Button
    private lateinit var toLoginButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        // If user is logged in send them to MainActivity
        if (currentUser != null) {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        submitButton = findViewById(R.id.registerButton)
        toLoginButton = findViewById(R.id.loginButton)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)

        submitButton.setOnClickListener { registerClick() }
        toLoginButton.setOnClickListener {
            val intent: Intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    fun registerClick() {
        // Get email and password
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()

        // Checking if email and password are empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "User Created!", Toast.LENGTH_SHORT).show()
                        val intent: Intent = Intent(this, Login::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Register", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
