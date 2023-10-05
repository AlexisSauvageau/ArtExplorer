package fr.alexis_hery.artexplorer

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import fr.alexis_hery.artexplorer.adapter.OeuvreAdapter

class OeuvrePopup(
    private val context: Context,
    private val oeuvre : OeuvreModel
) : Dialog(context){

    // la popup va s'afficher dans le contexte de notre adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_oeuvre)
        setupComponents()
    }

    // fonction qui met à jour les infos de la popup avec celles de l'oeuvre sélectionnée
    private fun setupComponents() {
        // image

        // titre
        val title = findViewById<TextView>(R.id.oeuvre_title)
        title.text = oeuvre.name

        // description
        val description = findViewById<TextView>(R.id.oeuvre_description)
        description.text = oeuvre.description

        // type
        val type = findViewById<TextView>(R.id.oeuvre_type)
        type.text = oeuvre.type

        // fermer la popup
        val closeBtn = findViewById<ImageView>(R.id.close_popup)
        closeBtn.setOnClickListener {
            dismiss()
        }
    }

}