package ca.qc.cgodin.slotmachinetp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ca.qc.cgodin.slotmachinetp.databinding.ActivityMainBinding
import ca.qc.cgodin.slotmachinetp.databinding.FragmentMachineBinding
import ca.qc.cgodin.slotmachinetp.databinding.FragmentStatsBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Contrôle la navugation
    private val navController by lazy {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHostFragment.navController
    }

    //Instance de partieViewMOdel pour supprimer la base de données
    private val partieViewModel: PartieViewModel by lazy {
        ViewModelProvider(
            this,
            PartieViewModelFactory(application)
        ).get(PartieViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //Bar d'action s'occupera du binding
        setSupportActionBar(binding.toolbar)
        binding.toolbar?.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.statsFragment -> {
                //Navigue au fragment des stats
                navController.navigate(R.id.statsFragment)
                true
            }

            R.id.refresh -> {
                partieViewModel.deleteEverything() //supprime la base de données
                navController.navigate(R.id.machine_fragment) // Quand on navigue au fragment de la machine(jeu) les attributs du fragment sont reinitialisés automatiquement, donc pas besoin de plus de code
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}