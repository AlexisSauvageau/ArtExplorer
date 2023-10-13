package fr.alexis_hery.artexplorer.storage

import android.content.Context
import fr.alexis_hery.artexplorer.OeuvreModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class OeuvreManager(private val context: Context) {
    val oeuvreList: ArrayList<OeuvreModel> = ArrayList()
    private val jsonFileName: String = "Data.json"

    init {
        loadDataFromAssets()
    }

     private fun getJSONFromAssets(): String? {
        var json: String?
        val charset: Charset = Charsets.UTF_8
        try {
            val oeuvreJSONFILE = context.assets.open(jsonFileName)
            val size = oeuvreJSONFILE.available()
            val buffer = ByteArray(size)
            oeuvreJSONFILE.read(buffer)
            oeuvreJSONFILE.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

     private fun loadDataFromAssets() {
        try {
            val json = JSONObject(getJSONFromAssets()!!)
            val oeuvreTab = json.getJSONArray("Data")


            for (i in 0 until oeuvreTab.length()) {
                val oeuvre = oeuvreTab.getJSONObject(i)
                val image = oeuvre.getString("0")
                val name = oeuvre.getString("1")
                val description = oeuvre.getString("2")
                val type = oeuvre.getString("3")
                val liked = oeuvre.getBoolean("4")

                val oeuvreDetails = OeuvreModel(image, name, description, type, liked)


                oeuvreList.add(oeuvreDetails)
            }
        } catch (e: JSONException) {

            e.printStackTrace()
        }
    }

    // fonction qui prend en paramètres un nom d'oeuvre, et qui like ou dislike l'oeuvre
    fun modifyOeuvreByName(oeuvreName: String) {
        // indice de l'oeuvre (-1 si elle n'est pas trouvée)
        val oeuvreIndex = oeuvreList.indexOfFirst { it.name == oeuvreName }

        // liker ou disliker l'oeuvre
        if(oeuvreIndex != -1) {
            oeuvreList[oeuvreIndex].liked = !oeuvreList[oeuvreIndex].liked
        }

        // mettre à jour les données
        saveDataToJson()
    }

    // fonction qui sauvegarde les données modifiées dans le fichier json
    fun saveDataToJson() {
        val jsonArray = JSONArray()
        for (oeuvre in oeuvreList) {
            val jsonObject = JSONObject()
            jsonObject.put("0", oeuvre.image)
            jsonObject.put("1", oeuvre.name)
            jsonObject.put("2", oeuvre.description)
            jsonObject.put("3", oeuvre.type)
            jsonObject.put("4", oeuvre.liked)
            jsonArray.put(jsonObject)
        }

        try {
            val jsonFile = context.openFileOutput(jsonFileName, Context.MODE_PRIVATE)
            jsonFile.write(jsonArray.toString().toByteArray())
            jsonFile.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
