package com.example.ejercicio14recyclerview

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/*
    5º Punto:

    - Hago una clase 'Adaptador', y le paso 2  parámetros de entrada, una lista mutable de tipo 'Disco' que hereda de RecyclerView.Adapter', que es de tipo 'Adaptador.ViewHolder' y otro parámetro de tipo 'Activity'
    - Hay que añadir dentro de la clase 'Adaptador' la clase 'ViewHolder' que hereda 'RecyclerView.ViewHolder(v)' porque sino da error, esta clase 'ViewHolder', tiene un parámetro de entrada 'v' que se lo va a pasar los tres métodos que tenemos que implementar
    - 'class Adaptador' sigue en rojo porque me obliga a implementar 3 métodos, nos ponemos encima y seleccionamos 'More Actions' y seleccionamos 'Implement members' y seleccionamos los 3 métodos
    - Le paso como segundo parámetro al 'Adaptador' una variable de tipo actividad
 */

class Adaptador(var listaDiscos:List<Disco>, var actividad: Activity):RecyclerView.Adapter<Adaptador.ViewHolder>() {

    // La clase 'ViewHolder' me dice lo que va a tener cada contenedor y le pongo 'inner' acceder a las propiedades del 'padre'

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // 6º Le tengo que decir las variables que van a cambiar dentro de mi contenedor --> Nombre de la pelicula, genero y año de estreno:
        var nombrePelicula: TextView
        var generoPelicula: TextView
        var anyoEstreno: TextView
        var fotoCaratula: ImageView
        var posicion:Int = 0

        // Aquí se genera automáticamente al instanciar los objetos de tipo 'ViewHolder' con el init
        init {
            // Capturo esas variables en la interface:
            nombrePelicula = v.findViewById(R.id.tv_nombrePelicula)
            generoPelicula = v.findViewById(R.id.tv_generoPelicula)
            anyoEstreno = v.findViewById(R.id.tv_anyoEstreno)
            fotoCaratula = v.findViewById(R.id.fondoImagen)

            v.setOnClickListener {
                // Pasar información del adaptador al fragment
                 val bundle = bundleOf("id" to this.posicion )
                // Aquí le digo de pasar el fragmento de la actividad del segundo fragmento al tercer fragmento en el xml de la carpeta 'navigation'
                (actividad as MainActivity).findNavController(R.id.nav_host_fragment).navigate(R.id.action_SecondFragment_to_thirdFragment, bundle)
            }
        }
    }
    // Esto está dentro de la clase principal 'Adaptador'

        // 7º Cargamos el layout 'item' y creamos un 'ViewHolder' lo devolvemos con el parámetro de entrada 'v'
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return ViewHolder(v)
        }

        // 8º Poner los datos de mi lista en mi contenedor
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            // Al parámetro de entrada 'holder' que es una variable de tipo 'ViewHolder', en la variable de mi 'viewHolder' accedo a la posición  y se la paso a 'position'
            // Le tengo que pasar  la posición  al 'bundle' de cada contenedor (que es cada barra con el Disco, estilo, año, url y canciones, llevan una 'id' que es al seleccional el holder)

            holder.posicion=listaDiscos[position].id
            holder.nombrePelicula.text = listaDiscos[position].nombreDisco
            holder.generoPelicula.text = listaDiscos[position].estilo
            holder.anyoEstreno.text = listaDiscos[position].AnyoEstreno.toString()

            // Hay que pasarle a la 'imagenView' una url de esta forma:
            Glide.with(actividad).load(listaDiscos[position].caratula).into(holder.fotoCaratula)
        }

        // 9º Cuantos elementos voy a tener que cargar en total
        override fun getItemCount(): Int {
            return listaDiscos.count()
        }
}