package com.example.ea_create.momosheduler

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.SimpleAdapter
import com.example.ea_create.momosheduler.Adapter.Operations_Adapter
import com.example.ea_create.momosheduler.Models.Operation
import com.example.ea_create.momosheduler.Models.UserCompte
import com.example.ea_create.momosheduler.Network.RetrofitInit
import com.example.ea_create.momosheduler.Network.RetrofitService
import kotlinx.android.synthetic.main.activity_operation_contener.*
import retrofit2.Call
import retrofit2.Response

class OperationContener : AppCompatActivity() {

    var operation:ArrayList<Operation> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_contener)
        var intent = intent
        var operator = intent.getStringExtra("operator")


        //change textView color-----
        txt_Operation_contener_titre.setTextColor(
            if (operator == "MOOV") resources.getColor(R.color.colorMoov)
            else resources.getColor(R.color.colorMTN)
        )


        //**------------

        // set an adapter

        var items = arrayListOf<String>("Item1", "Item1", "Item1", "Item1", "Item1", "Item1", "Item1", "Item1")
        var adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, items)
        ltv_operation.adapter = adapter




        fab.setOnClickListener { view ->
            startActivity(Intent(applicationContext, AddOperation::class.java))
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        MakeRequest()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.statistique, menu)
        return true
    }


    private fun MakeRequest() {

        val retro = RetrofitInit.retrofit().create(RetrofitService::class.java)
        var r = retro.gettodayOperationbyOperator(Global.operator)
        r.enqueue(object : retrofit2.Callback<ArrayList<Operation>> {

            override fun onFailure(call: Call<ArrayList<Operation>>, t: Throwable) {

                showDialog(
                    "Nous rencontrons des difficultés à nous connecté au serveur.\n Verifiez votre " +
                            "connexion "
                )
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<Operation>>, response: Response<ArrayList<Operation>>) {

                if(response.isSuccessful and ( response.body()!!.size>0)){
                    operation= response.body()!!


                    var adapter= Operations_Adapter(applicationContext,operation)
                    ltv_operation.adapter=adapter

                }

            }
        })

    }


    fun showDialog(text: String) {

        AlertDialog.Builder(this)
            .setMessage(text).show()
    }
}
