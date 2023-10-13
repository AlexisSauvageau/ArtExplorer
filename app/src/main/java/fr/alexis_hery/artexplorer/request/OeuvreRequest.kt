package fr.alexis_hery.artexplorer.request

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.alexis_hery.artexplorer.OeuvreModel
import fr.alexis_hery.artexplorer.storage.OeuvreJSONFileStorage
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.IOException

class OeuvreRequest(private val context: Context) {

    val lstOeuvres: ArrayList<OeuvreModel> = ArrayList()

    companion object {
        private const val URL = "http://51.68.91.213/gr-2-9/Data.json"
    }

    init{
        val queue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,
            {response ->
                val oeuvreTab = response.getJSONArray("Data")
                loadOeuvres(oeuvreTab)
                refresh(response)
                //updatable.update()

            },
            {error ->
                Toast.makeText(context, "Requête échouée", Toast.LENGTH_SHORT).show()

            }
        )
        queue.add(request)
        queue.start()
    }

    // fonction qui récupère les oeuvres en distanciel
    private fun loadOeuvres(oeuvreTab: JSONArray) {
        try {
            for (i in 0 until oeuvreTab.length()) {
                val oeuvre = oeuvreTab.getJSONObject(i)
                val id = oeuvre.getInt("id")
                val image = oeuvre.getString("image")
                val name = oeuvre.getString("name")
                val description = oeuvre.getString("description")
                val type = oeuvre.getString("type")
                val liked = oeuvre.getBoolean("liked")

                val oeuvreDetails = OeuvreModel(id, image, name, description, type, liked)

                lstOeuvres.add(oeuvreDetails)
            }
        } catch (e: JSONException) {

            e.printStackTrace()
        }
    }

    // fonction qui like ou dislike une oeuvre
    fun likeOrDislike(oeuvreId: Int){
        var oeuvre = OeuvreJSONFileStorage(context).find(oeuvreId)!!

        OeuvreJSONFileStorage(context).update(oeuvreId,
            OeuvreModel(
                oeuvreId,
                oeuvre.image,
                oeuvre.name,
                oeuvre.description,
                oeuvre.type,
                !oeuvre.liked
            )
        )
    }

    // fonction qui sauvegarde les données modifiées dans le fichier json
    fun saveDataToJson() {
        val jsonArray = JSONArray()
        for (oeuvre in lstOeuvres) {
            val jsonObject = JSONObject()
            jsonObject.put("id", oeuvre.id)
            jsonObject.put("image", oeuvre.image)
            jsonObject.put("name", oeuvre.name)
            jsonObject.put("description", oeuvre.description)
            jsonObject.put("type", oeuvre.type)
            jsonObject.put("liked", oeuvre.liked)
            jsonArray.put(jsonObject)
        }
        val obj = JSONObject()
        obj.put("Data", jsonArray)


    }

    private fun refresh(json: JSONObject) {
        delete()
        insert(json)
    }

    private fun delete() {
        for(oeuvre in OeuvreJSONFileStorage(context).findAll()){
            OeuvreJSONFileStorage(context).delete(oeuvre.id)
        }
    }

    private fun insert(json: JSONObject) {
        val oeuvres = json.getJSONArray("Data")

        for(i in 0 until oeuvres.length()){
            val oeuvre = oeuvres.getJSONObject((i))
            OeuvreJSONFileStorage(context).insert(
                OeuvreModel(
                    0,
                    oeuvre.getString(OeuvreModel.IMAGE),
                    oeuvre.getString(OeuvreModel.NAME),
                    oeuvre.getString(OeuvreModel.DESCRIPTION),
                    oeuvre.getString(OeuvreModel.TYPE),
                    oeuvre.getBoolean(OeuvreModel.LIKED)
                )
            )
        }
    }
}