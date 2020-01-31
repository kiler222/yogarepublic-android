package com.mysore.ashtanga.yoga.yogarepublic



import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Timetable(
    val results: String
//    val instructorName: String
){
    class Deserializer: ResponseDeserializable<Array<Timetable>> {
        override fun deserialize(content: String): Array<Timetable>? = Gson().fromJson(content, Array<Timetable>::class.java)
    }
}