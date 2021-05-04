package com.timeparadox.myshortstory.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// step 1 done..
@Entity(tableName = "AddNewStory")
data class AddStory(
    @ColumnInfo(name = "title") var storyTitle:String?=null,
    @ColumnInfo(name = "description") var storyDescription:String?=null,
    @PrimaryKey(autoGenerate = false) val storyID:Int?=null
)
