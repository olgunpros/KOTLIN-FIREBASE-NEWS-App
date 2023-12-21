package com.olgunbingol.dailynew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.olgunbingol.dailynew.adapter.FeedRecyclerAdapter
import com.olgunbingol.dailynew.databinding.ActivityLoginBinding
import com.olgunbingol.dailynew.databinding.ActivityMainBinding
import com.olgunbingol.dailynew.model.Post

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postArrayList : ArrayList<Post>
    private lateinit var feedAdapter : FeedRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        db = Firebase.firestore
        postArrayList = ArrayList<Post>()
        getData()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedRecyclerAdapter(postArrayList)
        binding.recyclerView.adapter = feedAdapter

    }
    private fun getData() {
        db.collection("Posts").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null) {
                Toast.makeText(this@MainActivity,error.localizedMessage,Toast.LENGTH_LONG).show()

            }
            else {
                if(value!= null) {
                    if(!value.isEmpty) {
                        val documents = value.documents
                        postArrayList.clear()
                        for(document in documents) {
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadurl = document.get("downloadUrl") as String

                            val post = Post(userEmail,comment,downloadurl)
                            postArrayList.add(post)
                        }
                        feedAdapter.notifyDataSetChanged()
                    }
                }
            }
        }


    }
    fun createClicked(view: View) {
      if(auth.currentUser != null) {
          val intent = Intent(this@MainActivity, UploadActivity::class.java)
          startActivity(intent)
      }
        else {
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
          startActivity(intent)
      }
    }
    fun settingsClicked(view: View) {
        val intent = Intent(this@MainActivity,SettingsActivity::class.java)
        startActivity(intent)

    }
    fun profileClicked(view: View) {
        if(auth.currentUser == null) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        else {
            val intent = Intent(this@MainActivity,SettingsActivity::class.java)
            startActivity(intent)
        }

    }

}