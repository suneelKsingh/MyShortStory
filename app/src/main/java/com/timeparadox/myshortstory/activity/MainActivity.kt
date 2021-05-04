package com.timeparadox.myshortstory.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timeparadox.myshortstory.R
import com.timeparadox.myshortstory.adapter.StoryAdapter
import com.timeparadox.myshortstory.databinding.ActivityMainBinding
import com.timeparadox.myshortstory.model.AddStory
import com.timeparadox.myshortstory.util.Constrains.UPDATE_BUTTON
import com.timeparadox.myshortstory.viewmodel.StoryViewModel
import kotlin.properties.Delegates

const val ADD_REQUEST = 1
const val EDIT_REQUEST = 2
const val TAG = "tag"
const val UPDATE_FOR = 3

class MainActivity : AppCompatActivity(), StoryAdapter.MenuItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storyViewModel: StoryViewModel
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var listener: StoryAdapter.MenuItemClickListener
    private var storyId by Delegates.notNull<Int>()
    private var storyItemPosition by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.toolbar.toolbar.title = "My Story"
        setSupportActionBar(binding.toolbar.toolbar)


        addStoryRequest()
        storyInRecyclerView()

        storyViewModel = ViewModelProviders.of(this)[StoryViewModel::class.java]
        storyViewModel.getAllStory().observe(this, Observer {
            storyAdapter.submitList(it)

        })
    }

    private fun addStoryRequest() {
        binding.addNewStory.setOnClickListener {
            UPDATE_BUTTON = 1
            val intent = Intent(this, AddEditActivity::class.java)
            startActivityForResult(intent, ADD_REQUEST)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, RIGHT or LEFT) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                storyItemPosition = viewHolder.adapterPosition
                if (viewHolder.adapterPosition >-1) {
                    when (direction) {
                        ItemTouchHelper.LEFT -> {
                            val story = storyAdapter.getStoryAt(viewHolder.adapterPosition)
                            storyViewModel.deleteStory(story)

                        }
                        ItemTouchHelper.RIGHT -> {
                            editStory(viewHolder.adapterPosition)
                        }
                    }
                }
            }
        }).attachToRecyclerView(binding.recyclerViewList)

    } // add Request End

    private fun storyInRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewList.setHasFixedSize(true)
        storyAdapter = StoryAdapter()
        storyAdapter.setOnClickListener(this)
        binding.recyclerViewList.adapter = storyAdapter
    }

    private fun popUpMenu(view: View, position: Int) {
        val popupMenu: PopupMenu = PopupMenu(applicationContext, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit -> {
                    val story = storyAdapter.getStoryAt(position)
                    var title = story.storyTitle
                    var description = story.storyDescription
                    storyId = story.storyID!!
                    val data = Bundle()
                    data.putString(EXTRA_ID, storyId.toString())
                    data.putString(STORY_TITLE, title)
                    data.putString(STORY_DESC, description)
                    val intent = Intent(this, AddEditActivity::class.java)
                    intent.putExtras(data)
                    startActivityForResult(intent, EDIT_REQUEST)
                }
                R.id.delete -> {
                    val story = storyAdapter.getStoryAt(position)
                    storyViewModel.deleteStory(story)
                    storyAdapter.notifyDataSetChanged()
                }
            }
            true
        })
        popupMenu.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == ADD_REQUEST && resultCode == Activity.RESULT_OK) {
            val title: String? = data.getStringExtra(STORY_TITLE)
            val description: String? = data.getStringExtra(STORY_DESC)
            storyViewModel.insert(AddStory(title, description))
            Toast.makeText(this, getString(R.string.story_added), Toast.LENGTH_SHORT).show()
        } else if (data != null && requestCode == EDIT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data.getIntExtra(EXTRA_ID, storyId)
            if (id == -1) {
                Toast.makeText(this, getString(R.string.no_update), Toast.LENGTH_SHORT).show()
                return
            }
            val title: String? = data.getStringExtra(STORY_TITLE)
            val description: String? = data.getStringExtra(STORY_DESC)
            storyViewModel.updateStory(AddStory(title, description, id))
//            storyAdapter.notifyDataSetChanged()
            Toast.makeText(this, getString(R.string.story_update), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.story_not_save), Toast.LENGTH_SHORT).show()
        }
    }

    private fun editStory(adapterPosition: Int) {
        val story = storyAdapter.getStoryAt(adapterPosition)
        var title = story.storyTitle
        var description = story.storyDescription
        storyId = story.storyID!!
        val data = Bundle()
        data.putString(EXTRA_ID, storyId.toString())
        data.putString(STORY_TITLE, title)
        data.putString(STORY_DESC, description)
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtras(data)
        startActivityForResult(intent, EDIT_REQUEST)

    }

    override fun onClick(view: View, position: Int) {
        popUpMenu(view, position)
    }

}
