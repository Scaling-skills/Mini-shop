package com.example.myapplication

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        button2.setOnClickListener {
            if (editTextTextEmailAddress3.text.isNullOrBlank() && editTextTextPassword2.text.isNullOrBlank()) {
                Toast.makeText(this, "Please fill the required fields", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "${editTextTextEmailAddress3.text} is logged in",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}