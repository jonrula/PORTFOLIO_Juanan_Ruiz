package com.example.ejercicio14recyclerview

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class FragmentUsuarioNuevo : Fragment() {

    var errores : String = ""



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario_nuevo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pongo nombre a la toolbar
        activity?.setTitle("Usuario nuevo")

        // Tener acceso al toolbar para acceder a los items
        setHasOptionsMenu(true)

        view.findViewById<Button>(R.id.button_id).setOnClickListener {
            // Capturo las cajas de texto de la ventana

            val usuarioNuevo = view.findViewById<EditText>(R.id.usuarioNuevo_id)
            val claveNueva = view.findViewById<EditText>(R.id.ClaveUsuarioNuevo_id)

            if (usuarioNuevo.text.isEmpty()){
                errores+= "Error !!, introduce el nombre del usuario/a\n"

                //Toast.makeText(activity, "Error !!, introduce el nombre del usuario/a", Toast.LENGTH_SHORT).show()
            }
            if (claveNueva.text.isEmpty()){
                errores+="Error !!, introduce la contraseña\n"
                //Toast.makeText(activity, "Error !!, introduce la contraseña", Toast.LENGTH_SHORT).show()
            }
            if (errores !=""){
                Toast.makeText(activity, errores, Toast.LENGTH_SHORT).show()
                errores=""
            }
            else{
                val datos: SharedPreferences = (activity as MainActivity).getSharedPreferences("datos", Context.MODE_PRIVATE)
                var editor: SharedPreferences.Editor = datos.edit()


                editor.putString("usuario",usuarioNuevo.text.toString())
                editor.putString("clave",claveNueva.text.toString())
                editor.apply()

                // Ahora al introducir el usuario nuevo va directamente al 'SecondFragment' sin loguearse de nuevo:

                Toast.makeText(activity, "Bienvenido/A ${usuarioNuevo.text.toString().toUpperCase()} !!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_fragmentUsuarioNuevo_to_SecondFragment)

                // Vacío las cajas de texto

                usuarioNuevo.setText("")
                claveNueva.setText("")

            }

        }

    }

    // Filtro los items que me interesan que salgan en el toolbar

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        menu.findItem(R.id.buttonGuardar).isVisible=false
        menu.findItem(R.id.buttonModificar).isVisible=false
        menu.findItem(R.id.buttonBorrar).isVisible=false

    }








}