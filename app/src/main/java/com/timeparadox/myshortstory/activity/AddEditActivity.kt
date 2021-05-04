package com.timeparadox.myshortstory.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.timeparadox.myshortstory.R
import com.timeparadox.myshortstory.databinding.ActivityAddEditBinding
import com.timeparadox.myshortstory.util.Constrains.UPDATE_BUTTON


const val EXTRA_ID = "EXTRA_ID"
const val STORY_TITLE = "TITLE"
const val STORY_DESC = "DESCRIPTION"

class AddEditActivity : AppCompatActivity(){
    private lateinit var addEditBinding: ActivityAddEditBinding
    private var storyId: Int = -1
    var uTitle:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit)
        addEditBinding.storyTitle.setText(intent.getStringExtra(STORY_TITLE))
        addEditBinding.description.setText(intent.getStringExtra(STORY_DESC))
        storyId = intent.getIntExtra(EXTRA_ID, -1)

        addEditBinding.toolbar.toolbar.title=uTitle
        setSupportActionBar(addEditBinding.toolbar.toolbar)

        if (UPDATE_BUTTON>0)
        {
            addEditBinding.saveButton.visibility=View.VISIBLE
            addEditBinding.update.visibility=View.GONE
            addEditBinding.saveButton.setOnClickListener {
                saveStory(it)
            }
            UPDATE_BUTTON=-1
        }
        else
        {
            addEditBinding.update.visibility=View.VISIBLE
            addEditBinding.saveButton.visibility=View.GONE

            addEditBinding.update.setOnClickListener {
                updateStory(it)
            }

        }

    }
    private fun updateStory(it: View) {
       saveStory(it)
         uTitle =addEditBinding.storyTitle.text.toString()
        var uDescription=addEditBinding.description.text.toString()
        val data=Intent()
        if (storyId !=-1)
        data.putExtra(EXTRA_ID, storyId)
        data.putExtra(STORY_TITLE, uTitle)
        data.putExtra(STORY_DESC, uDescription)
        setResult(Activity.RESULT_OK, data)
    }
    private fun saveStory(view: View) {
        var title = addEditBinding.storyTitle.text.toString()
        var description = addEditBinding.description.text.toString()

        if (title.isEmpty() || description.isEmpty()) {
            Snackbar.make(view, "please insert title and description", Snackbar.LENGTH_SHORT).show()
        } else {
            val storyData = Intent()
            if (storyId != -1)
                storyData.putExtra(EXTRA_ID, storyId)
            storyData.putExtra(STORY_TITLE, title)
            storyData.putExtra(STORY_DESC, description)
            setResult(Activity.RESULT_OK, storyData)

            finish()
        }
    }

}