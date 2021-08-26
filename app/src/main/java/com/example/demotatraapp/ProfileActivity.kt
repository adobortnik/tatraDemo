package com.example.demotatraapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.demotatraapp.ui.main.MainViewModel
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {
    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        val userId = intent.getIntExtra("id", 0)
        val name = findViewById<TextView>(R.id.name)
        val email = findViewById<TextView>(R.id.email)
        val avatar = findViewById<ImageView>(R.id.avatar)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel!!.loadUserDetail(userId).observe(this, { user ->
            name.text = user.first_name + user.last_name
            email.text = user.email
            Picasso.get().load(user.avatar).into(avatar)
            findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
        })
    }
}