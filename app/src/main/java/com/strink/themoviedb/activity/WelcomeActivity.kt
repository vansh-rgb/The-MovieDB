package com.strink.themoviedb.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.strink.themoviedb.R
import com.strink.themoviedb.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            intent.putExtra("Tag",binding.button2.tag.toString())
            startActivity(intent)
        }

        binding.button3.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            intent.putExtra("Tag",binding.button3.tag.toString())
            startActivity(intent)
        }

        binding.button4.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            intent.putExtra("Tag",binding.button4.tag.toString())
            startActivity(intent)
        }

        binding.button5.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            intent.putExtra("Tag",binding.button5.tag.toString())
            startActivity(intent)
        }

    }
}