package fr.alexis_hery.artexplorer

import android.graphics.Bitmap
import android.widget.ImageView

class OeuvreModel(
    val image: String = "",
    val name: String = "Titre",
    val description: String = "Description",
    val type: String = "Type",
    val liked: Boolean = false
)