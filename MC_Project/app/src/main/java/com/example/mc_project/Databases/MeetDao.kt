package com.example.mc_project.Databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MeetDao {

    @Query("SELECT * FROM Meets")
    fun getAll(): List<MeetEntity>

    @Query("SELECT * FROM Meets where Id= :id")
    fun getMeet(id:String): MeetEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meetEntity: MeetEntity)
}