package com.example.ea_create.momosheduler

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.ea_create.momosheduler.R.id.drawer_layout
import kotlinx.android.synthetic.main.activity_accueil.*
import kotlinx.android.synthetic.main.app_bar_accueil.*
import kotlinx.android.synthetic.main.content_accueil.*

class Accueil : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        setSupportActionBar(toolbar)



        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        //btn click listener

        btn_MTN.setOnClickListener { l->OpenOperationsPage("MTN") }
        btn_MOOV.setOnClickListener { l->OpenOperationsPage("MOOV") }
    }
    fun OpenOperationsPage(  operator: String){

        var intent=Intent(this,OperationContener::class.java)
        intent.putExtra("operator",operator)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.accueil, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_connection-> {
                startActivity(Intent(applicationContext,Connection::class.java))
                // Handle the camera action
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
