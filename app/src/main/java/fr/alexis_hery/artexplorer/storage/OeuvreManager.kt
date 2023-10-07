package fr.alexis_hery.artexplorer.storage

import android.content.Context
import fr.alexis_hery.artexplorer.OeuvreModel
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class OeuvreManager(private val context: Context) {
    val oeuvreList: ArrayList<OeuvreModel> = ArrayList()

    init {
        loadDataFromAssets()
    }

     private fun getJSONFromAssets(): String? {
        var json: String?
        val charset: Charset = Charsets.UTF_8
        try {
            val oeuvreJSONFILE = context.assets.open("Data.json")
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
}
