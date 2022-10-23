package com.example.simondice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    val colores = arrayOf("Verde", "Verde", "Verde", "Verde")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bInicio : Button = findViewById(R.id.inicioB)
        bInicio.setOnClickListener{
            empezar()
        }
    }

    fun elec(): String {

        val colRan = when(Random.nextInt(4) + 1){
            1 -> colores[0]
            2 -> colores[1]
            3 -> colores[2]
            else -> colores[3]
        }
        return colRan
    }

    fun empezar(){
        elec()
    }

}