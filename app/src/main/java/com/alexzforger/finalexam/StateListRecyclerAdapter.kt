package com.alexzforger.finalexam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StateListRecyclerAdapter(
    private val stateList: List<StateMetadata>,
    private val onItemClicked: (stateMetadata: StateMetadata) -> Unit
): RecyclerView.Adapter<StateListRecyclerAdapter.StateItemRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateItemRecyclerViewHolder {
        val inflateView = LayoutInflater.from(parent.context)
            .inflate(R.layout.state_item, parent, false)
        return StateItemRecyclerViewHolder(inflateView)
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    override fun onBindViewHolder(holder: StateItemRecyclerViewHolder, position: Int) {
        val stateMetadata = stateList[position]
        holder.bind(
            mapOfStateSlugAndFlags[stateMetadata.stateSlug]!!.getFlagUrl("w160"),
            stateMetadata.state
        ) {
            onItemClicked(stateMetadata)
        }
    }

    class StateItemRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val stateImage: ImageView = itemView.findViewById(R.id.flag_image)
        val stateLabelTextView: TextView = itemView.findViewById(R.id.flag_label)

        fun bind(stateImageUrl: String, stateLabel: String, onItemClicked: () -> Unit) {
            Glide.with(itemView.context)
                .load(stateImageUrl)
                .into(stateImage)

            stateLabelTextView.text = stateLabel

            itemView.setOnClickListener{ onItemClicked() }
        }
    }
}