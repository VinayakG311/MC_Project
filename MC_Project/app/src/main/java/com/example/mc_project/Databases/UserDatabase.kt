package com.example.mc_project.Databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userdao(): UserDao

    companion object {

        @Volatile
        private var db: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {

            return db ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).allowMainThreadQueries().build()
                db = instance

                instance
            }
        }


    }

}