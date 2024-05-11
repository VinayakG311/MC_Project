package com.example.mc_project.Databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MeetEntity::class], version = 2, exportSchema = false)
abstract class MeetDatabase: RoomDatabase() {
    abstract fun meetdao(): MeetDao

    companion object {

        @Volatile
        private var db: MeetDatabase? = null

        fun getDatabase(context: Context): MeetDatabase {

            return db ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MeetDatabase::class.java,
                    "Meet_database"
                ).allowMainThreadQueries().build()
                db = instance

                instance
            }
        }


    }

}