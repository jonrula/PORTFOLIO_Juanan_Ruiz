package com.example.ejercicio14recyclerview

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface PelisDao {
    @Insert
    suspend fun insertar(miDisco:Disco)

    @Delete
    suspend fun borrar(miDisco:Disco)


    @Update
    suspend fun mofificar(miDisco:Disco)

    @Query ("Select * from tablaDiscos")
    fun mostrarTodas(): Flow<List<Disco>>

    @Query("Select * from tablaDiscos where id like :id")
    fun buscarPorId(id:Int): Flow<Disco>

}