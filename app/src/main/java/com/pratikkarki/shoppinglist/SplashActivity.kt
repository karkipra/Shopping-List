package com.pratikkarki.shoppinglist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val showAnim = AnimationUtils.loadAnimation(this,
                R.anim.btnanim)
        tvLoading.startAnimation(showAnim)

        Handler().postDelayed({

            var intentStart = Intent()
            intentStart.setClass(this@SplashActivity, MainActivity::class.java)
            startActivity(intentStart)
        }, 6000)


    }
}

