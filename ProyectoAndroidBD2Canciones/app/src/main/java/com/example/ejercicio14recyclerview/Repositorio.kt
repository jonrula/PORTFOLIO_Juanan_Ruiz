package com.example.ejercicio14recyclerview

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class Repositorio(val miDao: PelisDao) {

    val listaDiscos : Flow<List<Disco>> = miDao .mostrarTodas()


    @Suppress("RedudantSuspendModifier")
    @WorkerThread
    suspend fun insertar(miDisco:Disco){
            miDao.insertar(miDisco)
        }

    @Suppress("RedudantSuspendModifier")
    @WorkerThread
    suspend fun borrar(miDisco: Disco){
        miDao.borrar(miDisco)
    }



    @Suppress("RedudantSuspendModifier")
    @WorkerThread
    suspend fun modificar(miDisco:Disco){
        miDao.mofificar(miDisco)
    }

    // El siguiente es una consulta (fun) y tiene un retorno para devolver datos

    @Suppress("RedudantSuspendModifier")
    @WorkerThread
    fun buscarPorId(id:Int):Flow<Disco>{
        return miDao.buscarPorId(id)

    }



}