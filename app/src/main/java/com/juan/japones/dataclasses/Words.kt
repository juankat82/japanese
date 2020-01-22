package com.juan.japones.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Words (
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id")
    var uid:Int? = 0,
    @ColumnInfo (name= "word_row")
    @NotNull var word:String,
    @ColumnInfo(name = "meaning_row")
    @NotNull var meaning:String)

