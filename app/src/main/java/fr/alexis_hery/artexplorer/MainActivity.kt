package fr.alexis_hery.artexplorer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import fr.alexis_hery.artexplorer.adapter.OeuvreAdapter

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

        val lstOeuvres = arrayListOf<OeuvreModel>()

        lstOeuvres.add(OeuvreModel("test.png", "La Joconde", "Tableau fait par Leonard de Vinci", "Peinture", false))
        lstOeuvres.add(OeuvreModel("test.png", "Star Wars", "Grande saga de films !", "Cinéma", true))
        lstOeuvres.add(OeuvreModel("test.png", "Le Sphinx", "Une oeuvre intemporelle", "Sculpture", true))
        lstOeuvres.add(OeuvreModel("test.png", "Star Wars", "Grande saga de films !", "Cinéma", false))
        lstOeuvres.add(OeuvreModel("test.png", "Le Sphinx", "Une oeuvre intemporelle", "Sculpture", true))
        lstOeuvres.add(OeuvreModel("test.png", "La Joconde", "Tableau fait par Leonard de Vinci", "Peinture", false))

        // récupérer le recycler view
        val oeuvreRecyclerView = findViewById<RecyclerView>(R.id.lst_oeuvres)

        // créer la vue pour chaque élément
        oeuvreRecyclerView.adapter = OeuvreAdapter(applicationContext, lstOeuvres)
    }
}