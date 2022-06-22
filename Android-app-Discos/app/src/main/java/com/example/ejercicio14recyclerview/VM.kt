package com.example.ejercicio14recyclerview

import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

// 1 Crear la clase VM de tipo viewModel con la lista y todas las funciones

class VM(private val miRepositorio:Repositorio):ViewModel() {
    var lista: LiveData<List<Disco>> = miRepositorio.listaDiscos.asLiveData()

    //var id: Int = 0

    lateinit var miDisco: LiveData<Disco>


        fun Insertar(album: Disco) = viewModelScope.launch(){
            miRepositorio.insertar(album)
        }

        fun Modificar(album: Disco) = viewModelScope.launch{
              miRepositorio.modificar(album)
        }

        fun Borrar(album: Disco) = viewModelScope.launch{
              miRepositorio.borrar(album)
        }

        fun buscarPorId(id: Int) = viewModelScope.launch{
            miDisco=miRepositorio.buscarPorId(id).asLiveData()

        }


}



class DiscosViewModelFactoria(private val repositorio: Repositorio):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VM::class.java)){
            @Suppress ("UNCHECKED CAST")
            return  VM(repositorio) as T
        }
        throw IllegalArgumentException("Clase View Model no reconocida")
    }

}