package fr.alexis_hery.artexplorer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.alexis_hery.artexplorer.OeuvreModel
import fr.alexis_hery.artexplorer.R

class OeuvreAdapter(
    private val context: Context,
    private val lstOeuvres: List<OeuvreModel>
    ) : RecyclerView.Adapter<OeuvreAdapter.ViewHolder>() {

    // représentation d'un item "oeuvre d'art"
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val colorBubble = view.findViewById<LinearLayout>(R.id.item_container) // couleur de l'oeuvre
        val image = view.findViewById<ImageView>(R.id.oeuvre_image)            // image de l'oeuvre
        val name = view.findViewById<TextView>(R.id.oeuvre_title)              // titre de l'oeuvre
        val type = view.findViewById<TextView>(R.id.oeuvre_type)               // type d'oeuvre
        val description = view.findViewById<TextView>(R.id.oeuvre_description) // description de l'oeuvre
        var liked = view.findViewById<ImageView>(R.id.star_icon)               // si l'oeuvre est likée
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_oeuvre, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oeuvre = lstOeuvres[position] // oeuvre courante

        // holder.image.set(oeuvre.image)
        holder.name.text = oeuvre.name               // mettre à jour le nom
        holder.description.text = oeuvre.description // mettre à jour la description
        holder.type.text = oeuvre.type               // mettre à jour le type

        // mettre à jour l'icône d'étoile si l'oeuvre est likée ou pas
        if(oeuvre.liked){
            holder.liked.setImageResource(R.drawable.ic_star)
        }
        else{
            holder.liked.setImageResource(R.drawable.ic_unstar)
        }

        // mettre à jour la couleur de la bulle
        if(oeuvre.type == "Peinture"){
            holder.colorBubble.setBackgroundColor(ContextCompat.getColor(context, R.color.lightblue))
        }
        else if(oeuvre.type == "Sculpture"){
            holder.colorBubble.setBackgroundColor(ContextCompat.getColor(context, R.color.lightgreen))
        }
        else if(oeuvre.type == "Cinéma"){
            holder.colorBubble.setBackgroundColor(ContextCompat.getColor(context, R.color.pink))
        }

    }

    override fun getItemCount(): Int = lstOeuvres.size
}