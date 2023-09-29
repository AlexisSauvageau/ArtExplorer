package fr.alexis_hery.artexplorer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

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
    }
}