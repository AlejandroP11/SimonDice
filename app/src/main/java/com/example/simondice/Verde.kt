package com.example.simondice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class Verde : AppCompatActivity() {
    val colores = arrayOf("Verde", "Rojo", "Azul", "Amarillo")
    val color = elec()
    val secuencia = arrayOf(color)
    var contadorS = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verde)
        dice(color)
        val bVerde : Button = findViewById(R.id.Bverde)
        bVerde.setOnClickListener{
            comprueba(bVerde, secuencia)
        }
        val bRojo : Button = findViewById(R.id.Brojo)
        bRojo.setOnClickListener{
            comprueba(bRojo, secuencia)
        }
        val bAzul : Button = findViewById(R.id.Bazul)
        bAzul.setOnClickListener{
            comprueba(bAzul, secuencia)
        }
        val bAmarillo : Button = findViewById(R.id.Bamarillo)
        bAmarillo.setOnClickListener{
            comprueba(bAmarillo, secuencia)
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

    fun dice(colRan: String){
        val manda: TextView = findViewById(R.id.manda)
        if(colRan.equals("Verde"))
            manda.setText(R.string.SGreen)
        else if(colRan.equals("Rojo"))
            manda.setText(R.string.SRed)
        else if(colRan.equals("Azul"))
            manda.setText(R.string.SBlue)
        else
            manda.setText(R.string.SYellow)
    }

    fun comprueba(view: View, secuencia: Array<String>){
        if(secuencia[contadorS].equals("Verde")){
            val Bprueba : Button = findViewById(R.id.Bverde)
            if (Bprueba.id == view.id)
                Toast.makeText(this, "Felicidades", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Lacagaste", Toast.LENGTH_SHORT).show()
        }
        if(secuencia[contadorS].equals("Rojo")){
            val Bprueba : Button = findViewById(R.id.Brojo)
            if (Bprueba.id == view.id)
                Toast.makeText(this, "Felicidades", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Lacagaste", Toast.LENGTH_SHORT ).show()
        }
        if(secuencia[contadorS].equals("Azul")){
            val Bprueba : Button = findViewById(R.id.Bazul)
            if (Bprueba.id == view.id)
                Toast.makeText(this, "Felicidades", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Lacagaste", Toast.LENGTH_SHORT ).show()
        }
        if(secuencia[contadorS].equals("Amarillo")){
            val Bprueba : Button = findViewById(R.id.Bamarillo)
            if (Bprueba.id == view.id)
                Toast.makeText(this, "Felicidades", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Lacagaste", Toast.LENGTH_SHORT ).show()
        }
    }
}