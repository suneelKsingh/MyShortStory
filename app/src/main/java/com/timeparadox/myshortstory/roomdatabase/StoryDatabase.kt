package com.timeparadox.myshortstory.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.timeparadox.myshortstory.model.AddStory
import com.timeparadox.myshortstory.util.subscribeOnBackground


// step 3 ... creating database
@Database(entities = [AddStory::class],version = 3)
abstract class StoryDatabase:RoomDatabase() {
    abstract fun addStoryDao():DAOAccess
companion object
{
        private var instance :StoryDatabase?=null
    @Synchronized
    fun getInstance(context: Context):StoryDatabase
    {
            if (instance==null)
            {
                instance=Room.databaseBuilder(context.applicationContext,StoryDatabase::class.java,"story_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build()
            }
        return instance!!
    }
    private val roomCallBack = object :Callback()
    {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            appStoryDefaultDB(instance!!)
        }
    }

    private fun appStoryDefaultDB(db: StoryDatabase) {
        val addStoryDao=db.addStoryDao()
            subscribeOnBackground {
                addStoryDao.insert(AddStory("First Story","Welcome This is our Very First Story"))
            }

    }
}
}