package com.example.harmanjeet.budgetplanner_finalproject

import android.app.Activity
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import kotlinx.android.synthetic.main.frame_animation.*

class AnimationActivity:AppCompatActivity() {

    val TAG: String? = "Hello"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frame_animation)

        val imageView = picture
        imageView.setBackgroundResource(R.drawable.dollar)

        val frameAnimation = imageView.background as AnimationDrawable
        frameAnimation.start()
        //frameAnimation.stop()

        val mHandler = Handler()

        val mLaunchTak = Runnable {
            var result = intent.getIntExtra("result", 0);

            var fm = supportFragmentManager
            var ft = fm.beginTransaction()

            val resultFragment = ResultFragment.newInstant(result)

            resultFragment.show(fm, TAG)
        }
        mHandler.postDelayed(mLaunchTak, 2500)
    }
}

