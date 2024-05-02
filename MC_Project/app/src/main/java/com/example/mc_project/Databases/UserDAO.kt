package com.example.mc_project.Databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM Users")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM Users where Username= :Username and Password= :Password")
    fun getUser(Username: String,Password: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg orientationEntity: UserEntity)
}