package com.example.mc_project.Databases

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun stringToList(string: String) = Gson().fromJson(string,Array<String>::class.java).toList()

    @TypeConverter
    fun listToString(list: List<String>) : String = Gson().toJson(list)

}