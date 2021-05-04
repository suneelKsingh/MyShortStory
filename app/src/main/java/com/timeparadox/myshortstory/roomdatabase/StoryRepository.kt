package com.timeparadox.myshortstory.roomdatabase

import android.app.Application
import androidx.lifecycle.LiveData
import com.timeparadox.myshortstory.model.AddStory
import com.timeparadox.myshortstory.util.subscribeOnBackground

class StoryRepository(application: Application) {
    private var addStoryDao: DAOAccess? = null
    private var stories:LiveData<List<AddStory>>? = null
    private val db = StoryDatabase.getInstance(application)

    init {
        addStoryDao = db.addStoryDao()
        stories = addStoryDao!!.getAllStory()
    }

    fun insert(addStory: AddStory) {
        subscribeOnBackground {
            addStoryDao!!.insert(addStory)
        }
    }

    fun getAllStory(): LiveData<List<AddStory>> {
        return stories!!
    }

    fun deleteStory(addStory: AddStory) {
        subscribeOnBackground {
            addStoryDao!!.deleteStory(addStory)
        }
    }

    fun updateStory(addStory: AddStory) {
        subscribeOnBackground {
            addStoryDao!!.updateStory(addStory)
        }
    }

}