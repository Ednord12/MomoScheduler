package com.example.ea_create.momosheduler

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuInflater
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_operation_contener.*

class OperationContener : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_contener)
        var intent=intent
        var operator=intent.getStringExtra("operator")



        //change textView color-----
        txt_Operation_contener_titre.setTextColor(if(operator=="MOOV") resources.getColor(R.color.colorMoov)
        else resources.getColor(R.color.colorMTN) )


        //**------------

        // set an adapter

        var items= arrayListOf<String>("Item1","Item1","Item1","Item1","Item1","Item1","Item1","Item1")
        var adapter=ArrayAdapter(applicationContext,android.R.layout.simple_list_item_1,items)
        ltv_operation.adapter=adapter




        fab.setOnClickListener { view ->
            startActivity(Intent(applicationContext,AddOperation::class.java))
           Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               .setAction("Action", null).show()
       }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.statistique,menu)
        return true
    }
}
