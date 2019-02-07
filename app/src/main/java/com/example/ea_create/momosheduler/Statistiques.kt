package com.example.ea_create.momosheduler

import android.app.AlertDialog
import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.TextView
import com.example.ea_create.momosheduler.Adapter.Operations_Adapter
import com.example.ea_create.momosheduler.Models.Operation
import com.example.ea_create.momosheduler.Network.RetrofitInit
import com.example.ea_create.momosheduler.Network.RetrofitService
import kotlinx.android.synthetic.main.activity_operation_contener.*
import kotlinx.android.synthetic.main.activity_statistiques.*
import retrofit2.Call
import retrofit2.Response

class Statistiques : AppCompatActivity() {

    var operation: ArrayList<Operation> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistiques)



        ltv_statistiques.setOnItemClickListener { parent, view, position, id ->


            OpenDialog(operation[position])

        }

        btn_stat_Ok.setOnClickListener { l -> getDate() }
    }

    override fun onResume() {
        super.onResume()
        //MakeRequest()
    }

    fun getDate() {
        var startd = txt_stat_startdate.text.trim().toString()
        var endd = txt_stat_enddate.text.trim().toString()
        if (startd.contains('-') and endd.contains('-')) {
            MakeRequest(startd, endd)
        } else {
            Snackbar.make(relativeLayout,"La période entrée ne respect pas le format, rééssayez",Snackbar.LENGTH_LONG)

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


        dg_id_ref.text = "ID Pièce: ${operation.customerIDcard}"
        dg_client.text = "Client: ${operation.customer}"
        dg_operation.text = "Opération: ${operation.actionMode}"
        dg_solde.text = "Montant: ${operation.solde}"
        dg_tel.text = "Téléphone: ${operation.phone}"
        dg_operator.text = "Operateur: ${operation.operator}"
        dg_tr_ref.text = "Tr Réference: ${operation.referencecode}"

        dialog.show()


    }

    private fun MakeRequest(st_d: String, end_d: String) {

        val retro = RetrofitInit.retrofit().create(RetrofitService::class.java)
        var r = retro.getOperationbyOperatorBetweenDate(Global.operator, st_d, end_d)
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


                    if (response.isSuccessful) {
                        operation = response.body()!!


                        var adapter = Operations_Adapter(applicationContext, operation)
                        ltv_statistiques.adapter = adapter

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



