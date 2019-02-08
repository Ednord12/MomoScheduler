package com.example.ea_create.momosheduler

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.ea_create.momosheduler.Models.UserCompte

import kotlinx.android.synthetic.main.content_accueil.*

class Accueil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar_accueil)


        //btn click listener

        btn_MTN.setOnClickListener { l -> OpenOperationsPage("MTN") }
        btn_MOOV.setOnClickListener { l -> OpenOperationsPage("MOOV") }
    }


    fun OpenOperationsPage(operator: String) {

        Global.operator = operator
        var intent = Intent(this, OperationContener::class.java)
        intent.putExtra("operator", operator)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.decn, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when (item?.itemId) {
            R.id.deconn -> {
                android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage("Voulez-vous deconnecter ce compte ?")
                    .setPositiveButton(
                        "Oui"

                    ) { _, _ ->
                        run {

                            Global.myConnectedUser = UserCompte()
                  //          finish()
                            startActivity(Intent(applicationContext, Connection::class.java))

                        }
                    }.setNegativeButton("Non") { dialog, which -> var v = 1 }
                    .create()
                    .show()
            }


        }

        return true
    }


    override fun onBackPressed() {

        android.support.v7.app.AlertDialog.Builder(this)
            .setMessage("Voulez-vous deconnecter ce compte ?")
            .setPositiveButton(
                "Oui"

            ) { _, _ ->
                run {

                    Global.myConnectedUser = UserCompte()
                    //          finish()
                   // startActivity(Intent(applicationContext, Connection::class.java))
                    super.onBackPressed()

                }
            }.setNegativeButton("Non") { dialog, which -> var v = 1 }
            .create()
            .show()

    }
}
