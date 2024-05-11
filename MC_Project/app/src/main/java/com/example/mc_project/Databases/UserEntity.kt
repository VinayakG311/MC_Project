package com.example.mc_project.Databases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Users")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo("Username") val username:String,

    @ColumnInfo("Password") val Password:String,

    @ColumnInfo(name = "Meetings") val meetings:String

){
}