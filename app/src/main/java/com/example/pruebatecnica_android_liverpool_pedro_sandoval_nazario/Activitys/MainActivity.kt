package com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.Fragments.ProductFragment
import com.example.pruebatecnica_android_liverpool_pedro_sandoval_nazario.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ProductFragment())
            .commit()
    }
}
