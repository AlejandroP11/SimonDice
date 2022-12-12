package com.example.simondice

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MyViewModel(application: Application): AndroidViewModel(application) {

    // lista mutable con la que llevaremos la cuenta de las rondas, además de poderla
    // ir actualizando en nuestra aplicación por ser LiveData
    val ronda = MutableLiveData<Int>()

    // lista en la que obtendremos la puntuación máxima de nuestro juego
    var record = MutableLiveData<Int>()

    // para filtrar en el Logcat
    private val TAG = "RealTime"
    // referencia a BD
    private var database: DatabaseReference


    // inicializamos variables cuando instanciamos
    init {
        ronda.value = 0

        //accedemos a la BD de Firebase
        database = Firebase.database("https://simon-dice-ead36-default-rtdb.firebaseio.com")
            .getReference("Datos")

        //definimos un Listener
        val esc = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                record.value = snapshot.getValue<Int>()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "esc.OnCancelled", error.toException())
            }
        }

        //añadimos el listener a la BD de Firebase
        database.addValueEventListener(esc)
    }

    // función que actualiza el record por el valor de la rondas que lleve el jugador
    fun actRecord(){
        record.value = ronda.value
        database.setValue(record.value)
    }

    // función que añade uno a la cantidad de rondas
    fun sumarRonda(): Int? {
        ronda.value = ronda.value?.plus(1)
        return ronda.value
    }

    // reinicia el valor de la ronda a cero
    fun newRonda() {
        ronda.value = 0
    }
}