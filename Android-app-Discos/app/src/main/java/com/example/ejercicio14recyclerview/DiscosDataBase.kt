package com.example.ejercicio14recyclerview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Disco::class), version=1, exportSchema= false)
abstract class  DiscosDataBase:RoomDatabase(){
    abstract fun  miDao():PelisDao

    companion object{

        @Volatile
        private var INSTANCE: DiscosDataBase?=null


            fun getDataBase(context: Context):DiscosDataBase {
                return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiscosDataBase::class.java,
                    "Base Discos"
                ).build()
                INSTANCE = instance
                instance

                 }
            }

    }
}
