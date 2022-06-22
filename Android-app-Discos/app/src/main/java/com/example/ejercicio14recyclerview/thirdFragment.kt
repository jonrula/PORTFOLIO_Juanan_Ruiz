package com.example.ejercicio14recyclerview

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class thirdFragment : Fragment() {

    private var id2 :Int = -2

    lateinit var nuevoNombre:EditText
    lateinit var generoPelicula:EditText
    lateinit var anyoEstreno :EditText
    lateinit var cancionesDisco: EditText
    lateinit var urlCaratula: EditText

    lateinit var miDisco: Disco



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_third, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true) // Esto es para cambiar el menú

        id2 =arguments?.getInt("id") ?:-1

        nuevoNombre = view.findViewById(R.id.nombrePelicula)
        generoPelicula = view.findViewById(R.id.generoPelicula)
        anyoEstreno  = view.findViewById(R.id.anyoEstreno)
        cancionesDisco = view.findViewById(R.id.etCanciones)
        urlCaratula = view.findViewById(R.id.eturl)


        if (id2 == -1) {

            activity?.setTitle("Insertar")

        }

        else{
            activity?.setTitle("Editar y borrar")


            (activity as MainActivity).miViewModels.buscarPorId(id2)
            (activity as MainActivity).miViewModels.miDisco.observe(activity as MainActivity){
                it?.let{
                    miDisco=it
                    nuevoNombre.setText(it.nombreDisco)
                    generoPelicula.setText(it.estilo)
                    anyoEstreno.setText(it.AnyoEstreno.toString())
                    cancionesDisco.setText(it.canciones)
                    urlCaratula.setText(it.caratula)
                }
            }
        }


        }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        // Filtro la visibilidad de los items en función si 'Inserto' o 'modifico y borro'

        if (id2 == -1) {
            // Solo insertar

            menu.findItem(R.id.buttonGuardar).isVisible=true
            menu.findItem(R.id.buttonModificar).isVisible=false
            menu.findItem(R.id.buttonBorrar).isVisible=false

        }else{
            // Solo modificar y borrar

            menu.findItem(R.id.buttonGuardar).isVisible=false
            menu.findItem(R.id.buttonModificar).isVisible=true
            menu.findItem(R.id.buttonBorrar).isVisible=true
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var errores : String =""

        when (item.itemId){
            R.id.buttonGuardar ->{

                if (nuevoNombre.text.isEmpty()){
                    errores+="Error !!, introduce un nombre\n"
                }
                if (generoPelicula.text.isEmpty()){
                    errores+="Error !!, introduce un género\n"
                }
                if (anyoEstreno.text.isEmpty()){
                    errores+="Error !!, introduce el año de estreno\n"
                }
                if (cancionesDisco.text.isEmpty()){
                    errores+="Error !! introduce al menos una canción\n"
                }
                if (urlCaratula.text.isEmpty()){
                    errores+="Error !!, introduce una dirección 'url' válida para descargar la carátula\n"
                }
                if (errores!=""){
                    Toast.makeText(activity, errores, Toast.LENGTH_SHORT).show()
                    errores=""
                }
                else {
                    //Primero Inserta y luego se va al segundo fragmento y saco mensaje de confirmación:

                    (activity as MainActivity).miViewModels.Insertar(album = Disco (nombreDisco = nuevoNombre.text.toString() ,estilo = generoPelicula.text.toString(), AnyoEstreno = anyoEstreno.text.toString().toInt(), canciones = cancionesDisco.text.toString(),caratula = urlCaratula.text.toString()))
                    findNavController().navigate(R.id.action_thirdFragment_to_SecondFragment)
                    Toast.makeText(activity, "El disco ${nuevoNombre.text.toString().toUpperCase()} ha sido añadido correctamente !", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.buttonModificar ->{
                // Primero Modifica y luego se va al segundo fragmento y saco mensaje de confirmación:

                (activity as MainActivity).miViewModels.Modificar(album = Disco (id2,nombreDisco = nuevoNombre.text.toString() ,estilo = generoPelicula.text.toString(), AnyoEstreno = anyoEstreno.text.toString().toInt(), canciones = cancionesDisco.text.toString(),caratula=urlCaratula.text.toString()))
                findNavController().navigate(R.id.action_thirdFragment_to_SecondFragment)
                Toast.makeText(activity, "El disco se ha modificado correctamente !", Toast.LENGTH_SHORT).show()

            }
            R.id.buttonBorrar ->{
                // Primero Borra y luego se va al segundo fragmento y saca mensaje de confirmación:

                (activity as MainActivity).miViewModels.Borrar(miDisco)
                findNavController().navigate(R.id.action_thirdFragment_to_SecondFragment)
                Toast.makeText(activity, "El disco se ha eliminado correctamente !", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}


