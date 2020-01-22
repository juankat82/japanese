package com.juan.japones.database

import androidx.room.*
import androidx.room.Dao
import com.juan.japones.dataclasses.Words

private const val DBNAME:String = "Word Database"

@Dao
interface WordsDao {

    @Insert
    fun insert(word:Words)

    @Query("SELECT * FROM words")
    fun query() : MutableList<Words>


    @Delete
    fun deleteOne(word:Words)

    @Query("DELETE FROM words")
    fun deleteAll()

    @Update
    fun updateRegistry(word:Words)
}