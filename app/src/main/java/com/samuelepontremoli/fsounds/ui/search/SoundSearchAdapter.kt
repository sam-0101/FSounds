package com.samuelepontremoli.fsounds.ui.search

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.samuelepontremoli.fsounds.R
import com.samuelepontremoli.fsounds.network.SoundPartialDetail
import com.samuelepontremoli.fsounds.utils.inflate

/**
 * FSounds - Created by s.pontremoli on 25/07/2017.
 */
class SoundSearchAdapter(var listSounds: MutableList<SoundPartialDetail>, val clickListener: SoundItemClickListener)
    : RecyclerView.Adapter<SoundSearchAdapter.SoundSearchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundSearchHolder {
        return SoundSearchHolder(parent.inflate(R.layout.item_sound_partial), clickListener)
    }

    override fun onBindViewHolder(holder: SoundSearchHolder, position: Int) {
        holder.bind(listSounds[position])
    }

    override fun getItemCount(): Int {
        return listSounds.size
    }

    fun setList(list: MutableList<SoundPartialDetail>) {
        listSounds = list
    }

    fun clearItems() {
        listSounds.clear()
    }

    class SoundSearchHolder(itemView: View?, val clickListener: SoundItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val nameView: TextView? = itemView?.findViewById(R.id.nameView)

        fun bind(sound: SoundPartialDetail) {
            nameView?.setText(sound.name)
            itemView.setOnClickListener { clickListener.soundClicked(sound.id) }
        }

    }

    interface SoundItemClickListener {
        fun soundClicked(soundId: Int)
    }

}
