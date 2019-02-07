package com.example.ea_create.momosheduler

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ea_create.momosheduler.Models.UserCompte
import com.example.ea_create.momosheduler.Network.RetrofitInit
import com.example.ea_create.momosheduler.Network.RetrofitService
import kotlinx.android.synthetic.main.activity_connection.*
import kotlinx.android.synthetic.main.activity_inscription.*
import retrofit2.Call
import retrofit2.Response

class Inscription : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        btn_incr_creer_compt.setOnClickListener { l ->
            checkImput()
        }
    }

    private fun checkImput() {

        if (incr_emeil.text.isNotBlank() and incr_prenom.text.isNotBlank() and incr_nom.text.isNotEmpty()
            or incr_telephone.text.isNotBlank() and incr_mdp1.text.isNotBlank() and incr_mdp2.text.isNotBlank()
        ) {
            if (incr_mdp1.text.toString() == incr_mdp2.text.toString()) {

                var userC = UserCompte(
                    0, incr_prenom.text.toString(), incr_nom.text.toString()
                    , incr_emeil.text.toString(), incr_telephone.text.toString(), incr_mdp1.text.toString()
                )
                makeRequest(userC)

            } else {
                Toast.makeText(
                    applicationContext,
                    "Mots de passe non identiques, réessayez",
                    Toast.LENGTH_LONG
                ).show()
            }

        } else {

            Toast.makeText(applicationContext, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
        }
    }

    private fun makeRequest(userCompte: UserCompte) {

        val retro = RetrofitInit.retrofit().create(RetrofitService::class.java)

        var r = retro.createUserComptes(userCompte)
        r.enqueue(object : retrofit2.Callback<UserCompte> {
            override fun onFailure(call: Call<UserCompte>, t: Throwable) {


                showDialog(
                    "Nous rencontrons des difficultés à nous connecté au serveur.\n Verifiez votre " +
                            "connexion "
                )
                t.printStackTrace()

            }

            override fun onResponse(call: Call<UserCompte>, response: Response<UserCompte>) {

                response.let {


                    if (response.isSuccessful) {
                        /* Toast.makeText(context,"Votre compte a été créé avec succes.\n Un email de validation à éte" +
                                 "envoyé à votre addresse email.Allez consulter",Toast.LENGTH_LONG).show()*/


                        showDialog(
                            "Votre compte a été créé avec succes.\n Un email de validation à éte" +
                                    "envoyé à votre addresse email.Allez consulter"
                        )
                        startActivity(Intent(applicationContext, Connection::class.java))
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
