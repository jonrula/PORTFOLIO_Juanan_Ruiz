package com.example.ejercicio14recyclerview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class SecondFragment : Fragment() {

    lateinit var miRecyclerView:RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // 11º Cargar la lista del fragmento en el contenedor
        val rootView = inflater.inflate(R.layout.fragment_second, container, false)
        var lista:List<Disco> = listOf()

        (activity as MainActivity).miViewModels.lista.observe(activity as MainActivity){Pelis ->
            Pelis?.let{
                lista=it
                // 14º Tengo que capturar la 'RecyclerView' de mi 'Fragment_second' con la vista :
                miRecyclerView= rootView.findViewById((R.id.recyclerView))
                // 15º Como se van a colocar los contenedores, uno debajo de otro:
                miRecyclerView.layoutManager=LinearLayoutManager(activity)
                /*
               3 Cambiar referencias de la listaDiscos a (activity as MainActivity).miViewModel.listaDiscos

               - Crear una instancia del adaptador, al que le paso la lista que está en el VM (que está en el MainActivity) y el mainActivity (los dos parámetros del adaptador)
               - Y esta instancia se la paso al adaptador del recyclerView
                */
                miRecyclerView.adapter=Adaptador(it,activity as MainActivity)


            }

        }


        return rootView

    }



    // La vista ya está creada y se la paso,


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        activity?.setTitle("Listado Discos")


        // Este es la captura del botón más
        view.findViewById<FloatingActionButton>(R.id.botonMas).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_thirdFragment)

        }

    }

    // Filtro los items que se verán en el toolbar
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.buttonInsertar).isVisible=false
        menu.findItem(R.id.buttonGuardar).isVisible=false
        menu.findItem(R.id.buttonModificar).isVisible=false
        menu.findItem(R.id.buttonBorrar).isVisible=false

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.buttonInsertar ->{
                findNavController().navigate(R.id.action_SecondFragment_to_thirdFragment)

            }

        }
        return super.onOptionsItemSelected(item)
    }



}