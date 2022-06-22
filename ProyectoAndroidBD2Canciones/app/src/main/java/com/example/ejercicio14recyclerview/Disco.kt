package com.example.ejercicio14recyclerview

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NonNls


/* 3 punto:
Hago una clase Disco,
*/



@Entity(tableName = "tablaDiscos")
data class Disco(
    @PrimaryKey (autoGenerate = true) var id: Int=0,
    @NonNull @ColumnInfo(name = "Disco") val nombreDisco: String,
    @NonNull @ColumnInfo(name = "Estilo") val  estilo: String,
    @NonNull @ColumnInfo(name = "AnyoEstreno") val AnyoEstreno: Int,
    @NonNull @ColumnInfo(name = "Canciones") val canciones: String,
    @NonNull @ColumnInfo(name = "Caratula") val caratula: String)

{}