package com.example.simondice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.system.exitProcess

var colorFondo = "" //variable que, una vez seleccionado, guardará el color de fondo

class MainActivity : AppCompatActivity() {

    val colores = arrayOf("Verde", "Rojo", "Azul", "Amarillo")
    var color = ""
    var secuencia = arrayOf("")
    var contadorS = 0
    var puntuacion = 0
    var empezar = false
    var fallo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hayamos el layout por su id para cambiar el color con los botones
        val layout : View = findViewById(R.id.layout)
        val layoutroot : View = layout.rootView

        //si la variable colorFondo no esta vacía llamamos a la función colorF
        if(!colorFondo.isEmpty())
            colorF(colorFondo, layoutroot)

        //Listeners para saber que botón es presionado y cambiar el color de fondo al del botón que se presionó
        val bVerde : Button = findViewById(R.id.Bverde)
        bVerde.setOnClickListener {
            layoutroot.setBackgroundColor(ContextCompat.getColor(this, R.color.greenB))
            colorFondo = "verde"
        }
        val bRojo : Button = findViewById(R.id.Brojo)
        bRojo.setOnClickListener {
            layoutroot.setBackgroundColor(ContextCompat.getColor(this, R.color.redB))
            colorFondo = "rojo"
        }
        val bAzul : Button = findViewById(R.id.Bazul)
        bAzul.setOnClickListener {
            layoutroot.setBackgroundColor(ContextCompat.getColor(this, R.color.blueB))
            colorFondo = "azul"
        }
        val bAmarillo : Button = findViewById(R.id.Bamarillo)
        bAmarillo.setOnClickListener {
            layoutroot.setBackgroundColor(ContextCompat.getColor(this, R.color.yellowB))
            colorFondo = "amarillo"
        }

        //boton inicio que al ser pulsado comienza el juego
        val bInicio : Button = findViewById(R.id.inicioB)
        bInicio.setOnClickListener{
            empezar = true
            color = empezar(bInicio)
            secuencia += color
            jugar() //corrutina
        }

        //botón restart que solo se visualiza si se pone pausa o se pierde en el juego
        val bRestart : Button = findViewById(R.id.restart)
        bRestart.setOnClickListener {
            val mIntent = intent //creación de un intento
            intent.putExtra("colorFondo", String()) //guardamos la variable colorFondo en el intento
            finish() //finaliza la actividad
            startActivity(mIntent) //vuelve a comenzar la actividad
        }

        //botón salie que solo se visualiza si se pone pausa o se pierde en el juego
        val bSalir : Button = findViewById(R.id.salir)
        bSalir.setOnClickListener{
            exitProcess(0)
        }

    }

    override fun onPause() {
        super.onPause()
        //si la variable empezar es true se llama a la función mPausar
        if(empezar)
            mPausar()
    }

    override fun onResume() {
        super.onResume()

        //botón que al ser clickeado llama a la función unpause
        val bPausa : Button = findViewById(R.id.pause)
        bPausa.setOnClickListener{
            unpause()
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

        //cambiar el valor de la puntuación si se comprueba que la secuencia fue colocada correctamente
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
        if(fallo)
            fallar()
    }

    //función que contiene una corrutina para cambiar la función de los botones para jugar
    @OptIn(DelicateCoroutinesApi::class)
    fun jugar(){
        GlobalScope.launch(Dispatchers.Main){
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
    }

    //función que se encarga de gestionar lo que pasa cuando el usuario falla o se sale del juego
    fun fallar(){
        val bVerde : Button = findViewById(R.id.Bverde)
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
        fallado.setText(R.string.fallo)
        fallado.visibility = View.VISIBLE
    }

    //función que genera un menú de pausa
    fun mPausar(){
        val bVerde : Button = findViewById(R.id.Bverde)
        bVerde.visibility = View.GONE
        val bRojo : Button = findViewById(R.id.Brojo)
        bRojo.visibility = View.GONE
        val bAzul : Button = findViewById(R.id.Bazul)
        bAzul.visibility = View.GONE
        val bAmarillo : Button = findViewById(R.id.Bamarillo)
        bAmarillo.visibility = View.GONE
        val manda: TextView = findViewById(R.id.manda)
        manda.visibility = View.GONE
        val bPause : Button = findViewById(R.id.pause)
        bPause.visibility = View.VISIBLE
        val bRestart : Button = findViewById(R.id.restart)
        bRestart.visibility = View.VISIBLE
        val bSalir : Button = findViewById(R.id.salir)
        bSalir.visibility = View.VISIBLE
        val pause : TextView = findViewById(R.id.fallo)
        pause.setText(R.string.pause)
        pause.visibility = View.VISIBLE
    }

    //función que "despausa" el juego
    fun unpause(){
        val bVerde : Button = findViewById(R.id.Bverde)
        bVerde.visibility = View.VISIBLE
        val bRojo : Button = findViewById(R.id.Brojo)
        bRojo.visibility = View.VISIBLE
        val bAzul : Button = findViewById(R.id.Bazul)
        bAzul.visibility = View.VISIBLE
        val bAmarillo : Button = findViewById(R.id.Bamarillo)
        bAmarillo.visibility = View.VISIBLE
        val manda: TextView = findViewById(R.id.manda)
        manda.visibility = View.VISIBLE
        val bPause : Button = findViewById(R.id.pause)
        bPause.visibility = View.GONE
        val bRestart : Button = findViewById(R.id.restart)
        bRestart.visibility = View.GONE
        val bSalir : Button = findViewById(R.id.salir)
        bSalir.visibility = View.GONE
        val fallado : TextView = findViewById(R.id.fallo)
        fallado.visibility = View.GONE
    }

    //función que compara la variable colorFondo para cambia el color del layout
    fun colorF(color : String, lr : View){
        if(color == "verde")
            lr.setBackgroundColor(ContextCompat.getColor(this, R.color.greenB))
        else if(color == "rojo")
            lr.setBackgroundColor(ContextCompat.getColor(this, R.color.redB))
        else if(color == "azul")
            lr.setBackgroundColor(ContextCompat.getColor(this, R.color.blueB))
        else
            lr.setBackgroundColor(ContextCompat.getColor(this, R.color.yellowB))
    }
}