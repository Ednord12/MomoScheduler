package com.example.ea_create.momosheduler

import android.app.AlertDialog
import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Validators.and
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.ea_create.momosheduler.Models.UserCompte
import com.example.ea_create.momosheduler.Network.RetrofitInit
import com.example.ea_create.momosheduler.Network.RetrofitService
import kotlinx.android.synthetic.main.activity_connection.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Connection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)


        //listener

        btn_connection.setOnClickListener { l ->


            login()

        }

        txt_creer_compte.setOnClickListener { l ->

            startActivity(Intent(applicationContext, Inscription::class.java))
        }


    }

    fun login() {
        val retro = RetrofitInit.retrofit().create(RetrofitService::class.java)

        var r = retro.login(txt_login.text.toString(), txt_password.text.toString())
        r.enqueue(object : retrofit2.Callback<ArrayList<UserCompte>> {
            override fun onResponse(call: Call<ArrayList<UserCompte>>, response: Response<ArrayList<UserCompte>>) {


                println("*****************************************")

                response.let {
                    if (response.isSuccessful and (response.body()!= null) and (response.body()!!.size>0)) {

//
                        /*************/
                        tv_login_incorrect.visibility = View.INVISIBLE


                        /********************/

                        println(response.body()?.get(0))
                        Global.myConnectedUser = response.body()!![0]
                        Global.myConnectedUser.connected = true



                        if (!Global.myConnectedUser.activate) {
                            showDialog("Ce compte n'est pas activé, clickez sur le lien qui vous" +
                            " a été envoyer par email")
                        }

                        if (Global.myConnectedUser.connected and Global.myConnectedUser.activate) {
                            finish()
                            startActivity(Intent(applicationContext, Accueil::class.java))
                        }
                    } else {

                        tv_login_incorrect.visibility = View.VISIBLE
                    }
                }


            }



            override fun onFailure(call: Call<ArrayList<UserCompte>>, t: Throwable) {

                t.printStackTrace()
                Log.e("Retrofit", t.stackTrace.toString())
                showDialog(
                    "Nous rencontrons des difficultés à nous connecté au serveur.\n Verifiez votre " +
                            "connexion "
                )


            }


        })

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
                    super.onBackPressed()

                }
            }.setNegativeButton("Non") { dialog, which -> var v = 1 }
            .create()
            .show()

    }


    fun showDialog(text: String) {

        AlertDialog.Builder(this)
            .setMessage(text).show()
    }
}
