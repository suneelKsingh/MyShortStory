package com.timeparadox.myshortstory.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

import com.timeparadox.myshortstory.model.AddStory

// step 2
@Dao
interface DAOAccess {

    @Insert
    fun insert(addStory: AddStory)
//
//    @Query("select * from AddNewStory order by title ASC")
//    fun getStory():LiveData<AddStory>

    @Query("select * from AddNewStory")
    fun getAllStory():LiveData<List<AddStory>>

    @Delete
    fun deleteStory(addStory: AddStory)

    @Update
    fun updateStory(addStory: AddStory)

}