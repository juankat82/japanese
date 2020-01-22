package com.juan.japones.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juan.japones.dataclasses.Words


@Database(entities = arrayOf(Words::class), version = 1, exportSchema = false)
abstract class RoomDatabaseClass : RoomDatabase()
{
    abstract fun wordsDao() : WordsDao
}