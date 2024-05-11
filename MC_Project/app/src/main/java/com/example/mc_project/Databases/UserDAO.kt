package com.example.mc_project.Databases

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.mc_project.model.user

@Dao
interface UserDao {

    @Query("SELECT * FROM Users")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM Users where Username= :Username ")
    fun getUser(Username: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Query("DELETE FROM Users")
    fun delete()
}