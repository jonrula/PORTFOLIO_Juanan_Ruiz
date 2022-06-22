package com.example.ejercicio14recyclerview

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.findNavController

/*
Pasos Recycler view:

1 Crear clase de lo que quiero mostrar (Disco)
2 Crear el layout para cada disco (itemXML) —> carátula, nombre Disco, género disco , año de estreno,

3 Crear clase adaptador
	class Adaptador (var listaDiscos MutableList<Disco):RecyclerView.Adapter<Adaptador.ViewHolder>(){
		class ViewHolder (v:View):RecyclerView.ViewHolder(v){
		onCreateViewHolder
		onBindViewHolder
		getItemCount
}

Para editar, borrar, insertar cada disco:

1 Crear la clase VM de tipo viewModel con la lista y todas las funciones
2 Crear un objeto ViewModel en la MainActivity
3 Cambiar referencias de la lista de discos a (activity as MainActivity).miViewModel.listaDiscos
4 En el ViewHolder del adaptador establecer el setOnclickListener
5 Para poder navegar desde el adaptador teneis que pasarle la actividad. La clase 'viewholder' tiene que ser 'inner' class.
6 Para poder pasar info del adaptador al fragment necesitas un bundle
7 Hacer el fragmento 3

*/

class MainActivity : AppCompatActivity() {

    val database by lazy {DiscosDataBase.getDataBase(this) }
    val miRepositorio by lazy {Repositorio(database.miDao())}
    /*
    2 Crear un objeto ViewModel en la MainActivity --> Instancio un objeto de tipo 'VM':
        Lo instancio aquí, porque la información del 'mainActivity' no desaparecería, se mantiene,  en cambio en los 'fragments' si desaparece al cambiar entre fragments
        Si tuviese varios 'activitys' iría en cada activity
    */

    val miViewModels:VM by viewModels{
        DiscosViewModelFactoria(miRepositorio)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || findNavController(R.id.nav_host_fragment).navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}