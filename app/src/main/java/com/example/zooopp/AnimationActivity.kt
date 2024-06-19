package com.example.zooopp

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_animation)

        val logo: ImageView = findViewById(R.id.logo)
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val scaleFade = AnimationUtils.loadAnimation(this, R.anim.scale_fade)

        logo.startAnimation(scaleUp)

        scaleUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                logo.startAnimation(scaleFade)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        scaleFade.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Transition to the main activity after the animation ends
                val intent = Intent(this@AnimationActivity, SplashScreenActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

    }
}