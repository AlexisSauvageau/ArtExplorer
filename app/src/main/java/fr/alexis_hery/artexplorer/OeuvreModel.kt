package fr.alexis_hery.artexplorer


class OeuvreModel(
    val image: String ,
    val name: String ,
    val description: String ,
    val type: String ,
    var liked: Boolean
){
    companion object {
        const val IMAGE = "image"
        const val NAME = "titre"
        const val DESCRIPTION = "description"
        const val TYPE = "type"
        const val LIKED = "liked"
    }

}
