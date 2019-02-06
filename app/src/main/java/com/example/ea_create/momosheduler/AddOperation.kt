package com.example.ea_create.momosheduler

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.example.ea_create.momosheduler.Models.Operation
import com.example.ea_create.momosheduler.Network.RetrofitInit
import com.example.ea_create.momosheduler.Network.RetrofitService
import kotlinx.android.synthetic.main.activity_add_operation.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AddOperation : AppCompatActivity() {

    lateinit var operation: Operation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_operation)
        btn_add_op.setOnClickListener { l -> AddOp() }
    }

    private fun AddOp() {

        if (txt_add_op_id_cart.text.isNotBlank() and txt_add_op_phone.text.isNotBlank()
            and txt_add_op_ref.text.isNotBlank() and txt_add_op_sold.text.isNotBlank()
        ) {


            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            var date = sdf.format(Date())
            operation = Operation(
                0, Global.operator, spi_operation.selectedItem.toString(),
                txt_add_op_id_cart.text.toString(),txt_add_op_client.text.toString(), txt_add_op_sold.text.toString(),
                txt_add_op_ref.text.toString(), date
            )


            val retro = RetrofitInit.retrofit().create(RetrofitService::class.java)
            var r=retro.createOperation(operation)
            r.enqueue(object :Callback<Operation>{
                override fun onFailure(call: Call<Operation>, t: Throwable) {


                    t.printStackTrace()
                }

                override fun onResponse(call: Call<Operation>, response: Response<Operation>) {

                    showDialog1(
                        "Nouvelle opération enregistrée avec succès"
                    )

                }
            })



        } else {


            Snackbar.make(linearLayout, "Veuillez remplir tous les champs", Snackbar.LENGTH_LONG).show()
        }
    }

    fun showDialog1(text: String) {

        AlertDialog.Builder(this)
            .setMessage(text)
            .setOnDismissListener {
                finish()
            }
            .show()
    }
}
