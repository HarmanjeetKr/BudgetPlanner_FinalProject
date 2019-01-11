package com.example.harmanjeet.budgetplanner_finalproject

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_expense_income.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var TAG = "Budget Traker"
    val TAG1: String? = "Harman"
    private val PERMISSIONS_CONTACTS = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Log.i("TAG","Harmanjeet Kaur")

        Log.i("TAG1","Welcome to Expense Traker")

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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
        menuInflater.inflate(R.menu.main, menu)
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

    private fun showAlertDialog(title: String, msg: String) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(msg)
        alertDialog.show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.calId -> {
                var i = Intent (this , Calculate::class.java);
                startActivity(i)
            }
            R.id.expenseId -> {
                val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.WRITE_CONTACTS),
                        PERMISSIONS_CONTACTS
                    )
                }
                var i = Intent(this , AddExpense::class.java)
                startActivity(i);
            }
            R.id.contactId -> {
                val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.READ_CONTACTS),
                        PERMISSIONS_CONTACTS
                    )
                }
               var i = Intent (this , ContactUs::class.java);
                startActivity(i);
            }
            R.id.aboutId -> {
                showAlertDialog("About","Budget Planner App helps to track on your expenses and income")
                return true
            }
            R.id.exitId -> {
                finish()
            }
            R.id.mapId -> {
                var i = Intent(this, MapActivity::class.java)
                startActivity(i)
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
