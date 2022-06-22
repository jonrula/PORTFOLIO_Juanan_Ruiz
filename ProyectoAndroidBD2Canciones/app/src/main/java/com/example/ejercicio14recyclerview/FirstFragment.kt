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
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.setTitle("Menú Logueo")

        setHasOptionsMenu(true) // Esto es para tener acceso al  menú a los items y poder ejecutar  los items u ocultarlos

        // Capturo las cajas de texto de la ventana del usuario y contraseña

        val usuario = view.findViewById<EditText>(R.id.usuario)
        val password = view.findViewById<EditText>(R.id.contrasenya)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {

        val datos:SharedPreferences = (activity as MainActivity).getSharedPreferences("datos", Context.MODE_PRIVATE)

            if (datos.getString("usuario", null) !=null) {
                // Capturo los usuario y clave de 'SharedPreferences'
                val usuarioSharedPreferences:String? = datos.getString("usuario",null)
                val claveSharedPreferences:String?= datos.getString("clave",null)

                if (usuario.text.isEmpty()){
                    Toast.makeText(activity, "Error !!, introduce el nombre del usuario/a", Toast.LENGTH_SHORT).show()
                }
                if (password.text.isEmpty()){
                    Toast.makeText(activity, "Error !!, introduce la contraseña", Toast.LENGTH_SHORT).show()
                }
                if  (usuarioSharedPreferences==usuario.text.toString() && claveSharedPreferences==password.text.toString()){

                    // Si coincide --> Mensaje de bienvenida y abro la ventana segunda:

                    Toast.makeText(activity, "Bienvenido/A ${usuario.text.toString().toUpperCase()} !!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    usuario.setText("")
                    password.setText("")

                }else{

                    // Si no coincide --> Mensaje de error y le digo que vuelva a intentarlo

                    Toast.makeText(activity, "Error !! el usuario/a ${usuario.text.toString().toUpperCase()} y/o ${password.text.toString()} no existe !!", Toast.LENGTH_SHORT).show()

                    // Vacío las cajas de texto
                    usuario.setText("")
                    password.setText("")
                }

            }else{

                // Si No existen usuarios  guardados en 'SharedPreferences' le digo que pulse el botón de crear usuario
                Toast.makeText(activity, "No existe el usuario/a ${usuario.text.toString().toUpperCase()}, pulsa el botón de 'Crear usuario'", Toast.LENGTH_SHORT).show()
                usuario.setText("")
                password.setText("")
            }

        }

        // Ir  al fragmento 'FragmentUsuarioNuevo' para crear un usuario/a nuevo

        view.findViewById<Button>(R.id.buttonCrearUsuario).setOnClickListener{

            findNavController().navigate(R.id.action_FirstFragment_to_fragmentUsuarioNuevo3)

        }


    }

    // Ocultamos los items del menú toolbar que no se van a utilizar
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        menu.findItem(R.id.buttonGuardar).isVisible=false
        menu.findItem(R.id.buttonModificar).isVisible=false
        menu.findItem(R.id.buttonBorrar).isVisible=false


    }







}