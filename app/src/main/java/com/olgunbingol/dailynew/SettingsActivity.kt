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
import com.olgunbingol.dailynew.databinding.ActivityMainBinding
import com.olgunbingol.dailynew.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        auth = Firebase.auth
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    fun signoutClicked(view: View) {
        if(auth.currentUser != null) {
            auth.signOut()
            val intent = Intent(this@SettingsActivity, MainActivity::class.java)
            startActivity(intent)
        }
        else {
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
        }



    }
}