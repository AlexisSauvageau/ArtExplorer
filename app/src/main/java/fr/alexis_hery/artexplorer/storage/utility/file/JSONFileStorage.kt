package fr.alexis_hery.artexplorer.storage.utility.file

import android.content.Context
import org.json.JSONObject

abstract class JSONFileStorage<T>(context: Context, name: String) : FileStorage<T>(context, name, ".json") {
    protected abstract fun objectToJson(obj: T): JSONObject
    protected abstract fun jsonToObject(json: JSONObject): T

    override fun dataToString(data: HashMap<Int, T>): String {
        val json = JSONObject()
        data.forEach{pair -> json.put("${pair.key}", objectToJson(pair.value))}
        return json.toString()
    }

    override fun stringToData(value: String): HashMap<Int, T> {
        val data = HashMap<Int, T>()
        val json = JSONObject(value)
        val iterator = json.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            data.put(key.toInt(), jsonToObject(json.getJSONObject(key)))
        }
        return data
    }

}