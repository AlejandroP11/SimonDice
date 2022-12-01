package com.example.simondice

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    // este va a ser nuestra lista para la secuencia random
    // usamos mutable, ya que la queremos modificar
    val contadorRonda = mutableListOf<Int>()
    // definimos una MutableLiveData
    // para poder observar los valores de la MutableList<Int>
    val livedata_contadorRonda = MutableLiveData<MutableList<Int>>()

    // inicializamos variables cuando instanciamos
    init {
        livedata_contadorRonda.value = contadorRonda
    }

    /**
     * añadimos entero random al
     */
    fun sumarRonda(): Int {
        if (contadorRonda.isEmpty())
            contadorRonda.add(0)
        var ultimoValor=contadorRonda[contadorRonda.lastIndex]
        ultimoValor++
        contadorRonda.add(ultimoValor)
        Log.d("estado", "aparece marcador de ronda")

        Log.d("estado", "ronda:" + contadorRonda)
        return contadorRonda[contadorRonda.lastIndex]
    }
}