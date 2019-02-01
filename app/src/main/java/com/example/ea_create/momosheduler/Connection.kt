package com.example.ea_create.momosheduler

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_connection.*

class Connection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)



        //listener


        txt_creer_compte.setOnClickListener { l->
            startActivity(Intent(applicationContext,Inscription::class.java))
        }
    }
}
