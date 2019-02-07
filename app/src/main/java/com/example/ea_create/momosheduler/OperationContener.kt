package com.example.ea_create.momosheduler

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import com.example.ea_create.momosheduler.Adapter.Operations_Adapter
import com.example.ea_create.momosheduler.Models.Operation
import com.example.ea_create.momosheduler.Models.UserCompte
import com.example.ea_create.momosheduler.Network.RetrofitInit
import com.example.ea_create.momosheduler.Network.RetrofitService
import kotlinx.android.synthetic.main.activity_operation_contener.*
import retrofit2.Call
import retrofit2.Response

class OperationContener : AppCompatActivity() {

    var operation: ArrayList<Operation> = arrayListOf()

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

        /*  //var items = arrayListOf<String>("Item1", "Item1", "Item1", "Item1", "Item1", "Item1", "Item1", "Item1")
          var items = arrayListOf<String>()
          var adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, items)
          //ltv_operation.adapter = adapter*/


        fab.setOnClickListener { view ->
            startActivity(Intent(applicationContext, AddOperation::class.java))
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        ltv_operation.setOnItemClickListener { parent, view, position, id ->


            OpenDialog(operation[position])

        }


    }

    private fun OpenDialog(operation: Operation) {

        var dialog: Dialog = Dialog(this)

        dialog.setContentView(R.layout.operation_details)


        var dg_client = dialog.findViewById<TextView>(R.id.txt_dg_client)
        var dg_solde = dialog.findViewById<TextView>(R.id.txt_dg_mintant)
        var dg_operation = dialog.findViewById<TextView>(R.id.txt_dg_operation)
        var dg_operator = dialog.findViewById<TextView>(R.id.txt_dg_opertor)
        var dg_tel = dialog.findViewById<TextView>(R.id.txt_dg_tel)
        var dg_tr_ref = dialog.findViewById<TextView>(R.id.txt_dg_tr_reference)
        var dg_id_ref = dialog.findViewById<TextView>(R.id.txt_dg_id_reference)
        var dg_agent = dialog.findViewById<TextView>(R.id.txt_dg_tr_agent)


        dg_id_ref.text = "ID Pièce: ${operation.customerIDcard}"
        dg_agent.text = "Agent: ${operation.agent}"
        dg_client.text = "Client: ${operation.customer}"
        dg_operation.text = "Opération: ${operation.actionMode}"
        dg_solde.text = "Montant: ${operation.solde}"
        dg_tel.text = "Téléphone: ${operation.phone}"
        dg_operator.text = "Operateur: ${operation.operator}"
        dg_tr_ref.text = "Tr Réference: ${operation.referencecode}"

        dialog.show()


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.accueil, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        when (item?.itemId) {
            R.id.action_settings -> {
                android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage("Voulez-vous deconnecter ce compte ?")
                    .setPositiveButton(
                        "Oui"

                    ) { _, _ ->
                        run {

                            Global.myConnectedUser = UserCompte()
                            finish()
                            startActivity(Intent(applicationContext, Connection::class.java))

                        }
                    }.setNegativeButton("Non") { dialog, which -> var v = 1 }
                    .create()
                    .show()


            }

            R.id.statistique -> {

                finish()
                startActivity(Intent(applicationContext, Statistiques::class.java))

            }

            R.id.myhome -> {

                finish()
                startActivity(Intent(applicationContext, Accueil::class.java))
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    override fun onResume() {
        super.onResume()

        MakeRequest()
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

                response.let {


                    if (response.isSuccessful ) {
                        operation = response.body()!!


                        var adapter = Operations_Adapter(applicationContext, operation)
                        ltv_operation.adapter = adapter

                    }

                }
            }
        })

    }


    fun showDialog(text: String) {

        AlertDialog.Builder(this)
            .setMessage(text).show()
    }
}
