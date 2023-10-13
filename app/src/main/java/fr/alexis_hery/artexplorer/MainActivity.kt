package fr.alexis_hery.artexplorer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.alexis_hery.artexplorer.adapter.OeuvreAdapter
import fr.alexis_hery.artexplorer.request.OeuvreRequest
import fr.alexis_hery.artexplorer.storage.OeuvreManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bouton pour ajouter une nouvelle oeuvre d'art
        val addOeuvreBtn = findViewById<Button>(R.id.add_button)
        addOeuvreBtn.setOnClickListener {

            val intent = Intent(applicationContext, AddOeuvreActivity::class.java)
            startActivity(intent)
        }

        val data = OeuvreRequest(this)
        val lstOeuvres = data.lstOeuvres

        // récupérer le recycler view
        val oeuvreRecyclerView = findViewById<RecyclerView>(R.id.lst_oeuvres)

        // créer la vue pour chaque élément
        oeuvreRecyclerView.adapter = OeuvreAdapter(applicationContext, lstOeuvres)

        // récupérer la barre de navigation
        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        navigation.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.favoris_page -> {
                    val intent = Intent(applicationContext, FavorisActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}