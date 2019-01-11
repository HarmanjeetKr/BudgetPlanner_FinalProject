package com.example.harmanjeet.budgetplanner_finalproject

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.frame_animation.*
import kotlinx.android.synthetic.main.result_fragment.*
import kotlinx.android.synthetic.main.result_fragment.view.*

class ResultFragment : DialogFragment(), View.OnClickListener {


     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val rootView = inflater.inflate(R.layout.result_fragment, container)

         var result = rootView.textresultId as TextView
         if(TextUtils.isEmpty(
             result.text)){
             result.text = arguments?.getInt("help_text").toString()
            Log.i("PASS", "Harman")
         } else {
            Log.i("PASS", "Error")
         }

         val button = rootView.findViewById(R.id.close_but_id) as Button
         button.setOnClickListener(this)

        return rootView

    }

    override fun onClick(v: View) {
        dismiss()
    }

    companion object {

        fun newInstant(textIdToPassToFragment: Int): ResultFragment {

            val m = ResultFragment()
            val bundle = Bundle()
            bundle.putInt("help_text", textIdToPassToFragment)
            m.arguments = bundle


            return m
        }
    }

}
