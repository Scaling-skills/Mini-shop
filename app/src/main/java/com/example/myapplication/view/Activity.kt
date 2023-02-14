package com.example.myapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.myapplication.R

class Activity : AppCompatActivity(R.layout.activity) {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){

            //
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<MFragment>(R.id.fragment_container_view)
            }

        }

    }

}