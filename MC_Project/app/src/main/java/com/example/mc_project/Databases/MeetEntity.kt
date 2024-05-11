package com.example.mc_project.Databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Meets")
data class MeetEntity(
    @PrimaryKey
    @ColumnInfo("Id") val Id:String,
//    @ColumnInfo("Moderator") val Moderator:String,
    @ColumnInfo("Participants") val Participants:String

){
}