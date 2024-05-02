package com.example.mc_project.Databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MeetDao {

    @Query("SELECT * FROM Meets")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM Meets where Id= :id")
    fun getMeet(id:String): UserEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg orientationEntity: UserEntity)
}