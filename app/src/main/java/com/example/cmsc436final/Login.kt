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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    private var auth = Firebase.auth

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
        setContentView(R.layout.activity_login)

        registerButton = findViewById(R.id.registerButton)
        loginButton = findViewById(R.id.loginButton)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)

        registerButton.setOnClickListener{
            val intent: Intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener { loginClick() }
    }

    fun loginClick() {
        // Get email and password
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()

        // Checking if email and password are empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            // Login in the user
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        val intent: Intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}