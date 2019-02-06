package com.example.ea_create.momosheduler

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        object :CountDownTimer(3000,1000)
            {
                override fun onFinish() {

                    CheckUser()
                }

                override fun onTick(millisUntilFinished: Long) {


                }

            }.start()



    }

    private fun CheckUser() {

        if (!Global.myConnectedUser.connected) {
            finish()

            startActivity(Intent(applicationContext,Connection::class.java))



        } else {


            finish()
            startActivity(Intent(applicationContext,Accueil::class.java))

        }


    }
}
