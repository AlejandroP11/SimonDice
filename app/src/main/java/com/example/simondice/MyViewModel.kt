package com.example.simondice

import android.annotation.SuppressLint
import android.app.Application
import android.util.MutableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class MyViewModel(application: Application): AndroidViewModel(application) {

    // lista mutable con la que llevaremos la cuenta de las rondas, además de poderla
    // ir actualizando en nuestra aplicación por ser LiveData
    val ronda = MutableLiveData<Int>()

    // lista en la que obtendremos la puntuación máxima de nuestro juego
    var record = MutableLiveData<Int>()

    // indicamos el contexto de la aplicacion e inicializamos el room
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private var room : RunDB? = null

    // inicializamos variables cuando instanciamos
    init {
        ronda.value = 0
        room = Room
            .databaseBuilder(context,
                RunDB::class.java, "Datos")
            .build()

        val roomCorrutine = GlobalScope.launch(Dispatchers.Main){
            try{
                record.value = room!!.userDao().getMaxPunt()
            } catch (ex : java.lang.NullPointerException) {
                room!!.userDao().insertPunt()
                record.value = room!!.userDao().getMaxPunt()
            }
        }
        roomCorrutine.start()
    }

    // función que actualiza el record por el valor de la rondas que lleve el jugador
    fun actRecord(){
        record.value = ronda.value
        val actCorrutine = GlobalScope.launch(Dispatchers.Main){
            room!!.userDao().update(Datos(1, ronda.value!!))
        }
        actCorrutine.start()
    }


    /**
     * añadimos uno a la ronda
     */
    fun sumarRonda(): Int? {
        ronda.value = ronda.value?.plus(1)
        return ronda.value
    }

    // reinicia el valor de la ronda a cero
    fun newRonda() {
        ronda.value = 0
    }
}