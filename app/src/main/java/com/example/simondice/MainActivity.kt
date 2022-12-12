package com.example.simondice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
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
    var empezar = false
    var fallo = false
    lateinit var layout : View
    lateinit var layoutroot : View
    lateinit var  bVerde : Button
    lateinit var  bRojo : Button
    lateinit var  bAzul : Button
    lateinit var  bAmarillo : Button
    lateinit var bInicio : Button
    lateinit var bRestart : Button
    lateinit var bPausa : Button
    lateinit var bSalir : Button
    lateinit var manda: TextView
    lateinit var fallado : TextView

    // instancia del ViewModel
    private val miModelo by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hayamos el layout por su id para cambiar el color con los botones
        layout = findViewById(R.id.layout)
        layoutroot = layout.rootView

        //si la variable colorFondo no esta vacía llamamos a la función colorF
        if(colorFondo.isNotEmpty())
            colorF(colorFondo, layoutroot)

        //Listeners para saber que botón es presionado y cambiar el color de fondo al del botón que se presionó
        bVerde = findViewById(R.id.Bverde)
        bVerde.setOnClickListener {
            layoutroot.setBackgroundColor(ContextCompat.getColor(this, R.color.greenB))
            colorFondo = "verde"
        }

        bRojo = findViewById(R.id.Brojo)
        bRojo.setOnClickListener {
            layoutroot.setBackgroundColor(ContextCompat.getColor(this, R.color.redB))
            colorFondo = "rojo"
        }

        bAzul = findViewById(R.id.Bazul)
        bAzul.setOnClickListener {
            layoutroot.setBackgroundColor(ContextCompat.getColor(this, R.color.blueB))
            colorFondo = "azul"
        }

        bAmarillo = findViewById(R.id.Bamarillo)
        bAmarillo.setOnClickListener {
            layoutroot.setBackgroundColor(ContextCompat.getColor(this, R.color.yellowB))
            colorFondo = "amarillo"
        }

        //declaración de los textview
        manda = findViewById(R.id.manda)
        fallado = findViewById(R.id.fallo)

        //botón inicio que al ser pulsado comienza el juego
        bInicio = findViewById(R.id.inicioB)
        bInicio.setOnClickListener{
            empezar = true
            color = empezar(bInicio)
            secuencia += color
            jugar() //corrutina
        }

        //botón restart que solo se visualiza si se pone pausa o se pierde en el juego
        bRestart = findViewById(R.id.restart)
        bRestart.setOnClickListener {
            miModelo.newRonda()
            val mIntent = intent //creación de un intento
            intent.putExtra("colorFondo", String()) //guardamos la variable colorFondo en el intento
            finish() //finaliza la actividad
            startActivity(mIntent) //vuelve a comenzar la actividad
        }

        //botón salie que solo se visualiza si se pone pausa o se pierde en el juego
        bSalir = findViewById(R.id.salir)
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
        bPausa = findViewById(R.id.pause)
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
        if(colRan == "Verde")
            manda.setText(R.string.SGreen)
        else if(colRan == "Rojo")
            manda.setText(R.string.SRed)
        else if(colRan == "Azul")
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
        if(comprobado[contadorS] == "Verde"){
            val bPrueba : Button = findViewById(R.id.Bverde)
            if (bPrueba.id != view.id)
                fallo = true
        }
        if(comprobado[contadorS] == "Rojo"){
            val bPrueba : Button = findViewById(R.id.Brojo)
            if (bPrueba.id != view.id)
                fallo = true
        }
        if(comprobado[contadorS] == "Azul"){
            val bPrueba : Button = findViewById(R.id.Bazul)
            if (bPrueba.id != view.id)
                fallo = true
        }
        if(comprobado[contadorS] == "Amarillo"){
            val bPrueba : Button = findViewById(R.id.Bamarillo)
            if (bPrueba.id != view.id)
                fallo = true
        }

        //cambiar el valor de la puntuación si se comprueba que la secuencia fue colocada correctamente
        val punt : TextView = findViewById(R.id.puntuacion)
        if(contadorS == secuencia.size - 1) {
            contadorS = 0
            //puntuacion++
            miModelo.sumarRonda()
            miModelo.ronda.observe(
                this,
                androidx.lifecycle.Observer(
                    fun (si: Int) {
                        if (miModelo.ronda.value != 0)
                            punt.text = miModelo.ronda.value.toString()
                    }
                )
            )
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
            bVerde.setOnClickListener{
                comprueba(bVerde, secuencia)
            }
            bRojo.setOnClickListener{
                comprueba(bRojo, secuencia)
            }
            bAzul.setOnClickListener{
                comprueba(bAzul, secuencia)
            }
            bAmarillo.setOnClickListener{
                comprueba(bAmarillo, secuencia)
            }
        }
    }

    //función que se encarga de gestionar lo que pasa cuando el usuario falla o se sale del juego
    fun fallar(){
        if(miModelo.ronda.value!! > miModelo.record.value!!){
            miModelo.actRecord()
            Toast.makeText(applicationContext, "¡FELICIDADES! Lograste un nuevo récord", Toast.LENGTH_SHORT).show()
        }
        bVerde.visibility = View.GONE
        bRojo.visibility = View.GONE
        bAzul.visibility = View.GONE
        bAmarillo.visibility = View.GONE
        manda.visibility = View.GONE
        bRestart.visibility = View.VISIBLE
        bSalir.visibility = View.VISIBLE
        fallado.setText(R.string.fallo)
        fallado.visibility = View.VISIBLE
    }

    //función que genera un menú de pausa
    fun mPausar(){
        bVerde.visibility = View.GONE
        bRojo.visibility = View.GONE
        bAzul.visibility = View.GONE
        bAmarillo.visibility = View.GONE
        manda.visibility = View.GONE
        bPausa.visibility = View.VISIBLE
        bRestart.visibility = View.VISIBLE
        bSalir.visibility = View.VISIBLE
        fallado.setText(R.string.pause)
        fallado.visibility = View.VISIBLE
    }

    //función que "despausa" el juego
    fun unpause(){
        bVerde.visibility = View.VISIBLE
        bRojo.visibility = View.VISIBLE
        bAzul.visibility = View.VISIBLE
        bAmarillo.visibility = View.VISIBLE
        manda.visibility = View.VISIBLE
        bPausa.visibility = View.GONE
        bRestart.visibility = View.GONE
        bSalir.visibility = View.GONE
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