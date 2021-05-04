package com.timeparadox.myshortstory.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.timeparadox.myshortstory.model.AddStory
import com.timeparadox.myshortstory.roomdatabase.StoryRepository

class StoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StoryRepository(application)
    private val allStory = repository.getAllStory()

    fun insert(addStory: AddStory) {
        repository.insert(addStory)
    }

    fun updateStory(addStory: AddStory) {
        repository.updateStory(addStory)
    }

    fun deleteStory(addStory: AddStory) {
        repository.deleteStory(addStory)
    }

    fun getAllStory(): LiveData<List<AddStory>> {
        return allStory
    }

}