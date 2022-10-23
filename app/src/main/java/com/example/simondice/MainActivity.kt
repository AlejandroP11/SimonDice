package com.example.simondice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    val colores = arrayOf("Verde", "Rojo", "Azul", "Amarillo")
    var color = ""
    var secuencia = arrayOf("")
    var contadorS = 0
    var puntuacion = 0
    var fallo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bInicio : Button = findViewById(R.id.inicioB)
        bInicio.setOnClickListener{
            color = empezar(bInicio)
            secuencia += color
        }
    }

    override fun onStart() {
        super.onStart()
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
        val bRestart : Button = findViewById(R.id.restart)
        bRestart.setOnClickListener{
            recreate()
        }
        val bSalir : Button = findViewById(R.id.salir)
        bSalir.setOnClickListener{
            exitProcess(0)
        }
    }

    //función que elige de manera aleatoria un color del Array colores
    fun elec(): String {

        val colRan = when(Random.nextInt(4) + 1){
            1 -> colores[0]
            2 -> colores[1]
            3 -> colores[2]
            else -> colores[3]
        }
        return colRan
    }

    //función que enseña en pantalla el color que se debe presionar
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

    //función que empieza el juego sacando por pantalla el primer color y esconde el boton de inicio
    fun empezar(boton: Button): String {
        val colorI = elec()
        boton.visibility = View.GONE
        dice(colorI)
        return colorI
    }

    //función que comprueba que los colores sean presionados en el orden indicado
    fun comprueba(view: View, comprobado: Array<String>){
        contadorS ++
        if(comprobado[contadorS].equals("Verde")){
            val Bprueba : Button = findViewById(R.id.Bverde)
            if (Bprueba.id != view.id)
                fallo = true
        }
        if(comprobado[contadorS].equals("Rojo")){
            val Bprueba : Button = findViewById(R.id.Brojo)
            if (Bprueba.id != view.id)
                fallo = true
        }
        if(comprobado[contadorS].equals("Azul")){
            val Bprueba : Button = findViewById(R.id.Bazul)
            if (Bprueba.id != view.id)
                fallo = true
        }
        if(comprobado[contadorS].equals("Amarillo")){
            val Bprueba : Button = findViewById(R.id.Bamarillo)
            if (Bprueba.id != view.id)
                fallo = true
        }

        val punt : TextView = findViewById(R.id.puntuacion)
        if(contadorS == secuencia.size - 1) {
            contadorS = 0
            puntuacion++
            val Spun = puntuacion.toString()
            punt.setText(Spun)
            color = elec()
            secuencia += color
            dice(color)
        }

        if(fallo){
            fallar()
        }
    }

    //función que se encarga de gestionar lo que pasa cuando el usuario falla
    fun fallar(){
        val bVerde : Button = findViewById<Button?>(R.id.Bverde)
        bVerde.visibility = View.GONE
        val bRojo : Button = findViewById(R.id.Brojo)
        bRojo.visibility = View.GONE
        val bAzul : Button = findViewById(R.id.Bazul)
        bAzul.visibility = View.GONE
        val bAmarillo : Button = findViewById(R.id.Bamarillo)
        bAmarillo.visibility = View.GONE
        val manda: TextView = findViewById(R.id.manda)
        manda.visibility = View.GONE
        val bRestart : Button = findViewById(R.id.restart)
        bRestart.visibility = View.VISIBLE
        val bSalir : Button = findViewById(R.id.salir)
        bSalir.visibility = View.VISIBLE
        val fallado : TextView = findViewById(R.id.fallo)
        fallado.visibility = View.VISIBLE

    }
}