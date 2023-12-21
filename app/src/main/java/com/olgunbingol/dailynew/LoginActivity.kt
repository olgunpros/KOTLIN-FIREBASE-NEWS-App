package com.olgunbingol.dailynew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.olgunbingol.dailynew.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
      auth = Firebase.auth
    }
    fun signinClicked(view: View) {
        if(auth.currentUser != null) {
            val intent = Intent(this@LoginActivity, UploadActivity::class.java)
            startActivity(intent)
            finish()

        }
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
            Toast.makeText(this, "Enter email and password!", Toast.LENGTH_LONG).show()

        } else {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                val intent = Intent(this@LoginActivity, UploadActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this@LoginActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }
    fun signupClicked(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
            Toast.makeText(this, "Enter email and password!", Toast.LENGTH_LONG).show()

        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                val intent = Intent(this@LoginActivity, UploadActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this@LoginActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}