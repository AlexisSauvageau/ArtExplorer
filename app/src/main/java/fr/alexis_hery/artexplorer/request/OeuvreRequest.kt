package fr.alexis_hery.artexplorer.request

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.alexis_hery.artexplorer.OeuvreModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException

class OeuvreRequest(private val context: Context) {

    val lstOeuvres: ArrayList<OeuvreModel> = ArrayList()

    companion object {
        private const val URL = "http://51.68.91.213/gr-2-9/Data.json"
    }

    // fonction qui récupère les oeuvres depuis internet (ou alors en local si le fichier existe déjà)
    fun getOeuvres(callback : () -> Unit){
        val file = File(context.filesDir, "Data.json")
        if (file.exists()) {
            // Charger les données à partir du fichier local
            callback()
        }
        else{
            val queue = Volley.newRequestQueue(context)
            val request = JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                {response ->
                    val oeuvreTab = response.getJSONArray("Data")
                    saveJsonData(oeuvreTab)
                    callback()

                },
                {error ->
                    Toast.makeText(context, "Requête échouée", Toast.LENGTH_SHORT).show()

                }
            )
            queue.add(request)
            queue.start()
        }
    }

    // fonction qui like ou dislike une oeuvre
    fun likeOrDislike(oeuvreId: Int){
        // Lire le fichier JSON existant
        val fileInputStream = context.openFileInput("Data.json")
        val jsonString = fileInputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)

        // Chercher et mettre à jour l'élément avec l'id donné
        for (i in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val currentId = jsonObject.getInt("id")
            if (currentId == oeuvreId) {
                // Inverser la valeur de l'attribut "liked"
                val currentLiked = jsonObject.getBoolean("liked")
                jsonObject.put("liked", !currentLiked)
                break
            }
        }

        // Réécrire le fichier JSON mis à jour
        saveJsonData(jsonArray)
    }

    // fonction qui sauvegarde les données modifiées dans le fichier json
    fun saveJsonData(jsonArray: JSONArray) {
        try {
            val fileOutputStream = context.openFileOutput("Data.json", Context.MODE_PRIVATE)
            fileOutputStream.write(jsonArray.toString().toByteArray())
            fileOutputStream.close()
            //Toast.makeText(context, "Données mises à jour dans Data.json", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Erreur lors de la mise à jour des données", Toast.LENGTH_SHORT).show()
        }
    }

    // fonction qui récupère les données du json local
    fun loadJsonData(): ArrayList<OeuvreModel> {
        val res: ArrayList<OeuvreModel> = ArrayList()

        try {
            val fileInputStream = context.openFileInput("Data.json")
            val jsonString = fileInputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val image = jsonObject.getString("image")
                val name = jsonObject.getString("name")
                val description = jsonObject.getString("description")
                val type = jsonObject.getString("type")
                val liked = jsonObject.getBoolean("liked")

                val oeuvre = OeuvreModel(id, image, name, description, type, liked)
                res.add(oeuvre)
            }
        }
        catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
        return res
    }
}