package com.example.harmanjeet.budgetplanner_finalproject

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.calculate.*
import kotlin.math.exp

class Calculate : AppCompatActivity (){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.calculate);

        var income = editincmId.text
        var expense = editexpId.text



        btnId.setOnClickListener {

            if(income.isNotEmpty() && expense.isNotEmpty()) {
                var result = income.toString().toInt() - expense.toString().toInt()

                var i = Intent(this, AnimationActivity::class.java)
                i.putExtra("result", result)
                startActivity(i);
            }else {
                Toast.makeText(
                    this,
                    R.string.errMsg,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}