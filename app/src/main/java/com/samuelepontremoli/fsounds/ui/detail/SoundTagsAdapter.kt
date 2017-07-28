package com.samuelepontremoli.fsounds.ui.detail

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.samuelepontremoli.fsounds.R
import com.samuelepontremoli.fsounds.utils.inflate

/**
 * FSounds - Created by s.pontremoli on 28/07/2017.
 */
class SoundTagsAdapter(var tags: MutableList<String>) : RecyclerView.Adapter<SoundTagsAdapter.SoundTagsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundTagsHolder {
        return SoundTagsHolder(parent.inflate(R.layout.item_sound_tag))
    }

    override fun onBindViewHolder(holder: SoundTagsHolder, position: Int) {
        holder.bind(tags[position])
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    fun setList(list: MutableList<String>) {
        tags = list
    }

    fun clearItems() {
        tags.clear()
    }

    class SoundTagsHolder(itemView: View?): RecyclerView.ViewHolder(itemView) {

        val tagTitle: TextView? = itemView?.findViewById(R.id.tagTitle)

        fun  bind(tag: String) {
            tagTitle?.setText(tag)
        }

    }


}

