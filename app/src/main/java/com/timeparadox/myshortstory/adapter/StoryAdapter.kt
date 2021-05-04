package com.timeparadox.myshortstory.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timeparadox.myshortstory.R
import com.timeparadox.myshortstory.model.AddStory


class StoryAdapter() :
    ListAdapter<AddStory, StoryAdapter.Holder>(diffCallback) {

    private var listener: MenuItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.story_list_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(getItem(position))
        {
            holder.title.text = storyTitle
            holder.description.text = storyDescription
        }
        holder.menuButton.setOnClickListener {
            listener?.onClick(holder.menuButton,position)
        }
//        holder.colorCard()


    }

    fun getStoryAt(position: Int): AddStory = getItem(position)


    //    inner class Holder(val binding: StoryListLayoutBinding) : RecyclerView.ViewHolder(binding.root)  // binding ...
    inner class Holder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById(R.id.cardStoryTitle) as TextView
        val description = itemView.findViewById(R.id.cardStoryDescription) as TextView
        val menuButton = itemView.findViewById(R.id.menuButton) as AppCompatImageView
        val cardView =itemView.findViewById(R.id.storyCard) as CardView
        val storyImage =itemView.findViewById(R.id.storyImage) as ImageView

//
//        fun colorCard()
//        {
////            var random =Random()
////            var colorCode:Int =Color.argb(255,random.nextInt(R.color.teal_200),
////                random.nextInt(R.color.white),
////                random.nextInt(R.color.purple_200))
////            cardView.setCardBackgroundColor(colorCode)
////            cardView.setCardBackgroundColor()
//        }

    }

    interface MenuItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setOnClickListener(listener: MenuItemClickListener) {
        this.listener = listener
    }
}

private val diffCallback = object : DiffUtil.ItemCallback<AddStory>() {
    override fun areItemsTheSame(oldItem: AddStory, newItem: AddStory) =
        oldItem.storyID == newItem.storyID

    override fun areContentsTheSame(oldItem: AddStory, newItem: AddStory) =
        oldItem.storyTitle == newItem.storyTitle && oldItem.storyDescription == newItem.storyDescription
}

