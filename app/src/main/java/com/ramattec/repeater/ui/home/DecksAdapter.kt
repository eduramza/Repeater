package com.ramattec.repeater.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramattec.repeater.databinding.ItemDeckBinding
import com.ramattec.repeater.domain.entity.deck.DeckEntity

class DecksAdapter(
    private val onClick: (deckId: String) -> Unit,
    private val onLongClick: (deckId: String) -> Unit
) : ListAdapter<DeckEntity, DecksAdapter.ViewHolder>(DeckDiffCallback()) {

    inner class ViewHolder(private val binding: ItemDeckBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: DeckEntity) {
            binding.tvDeckTitle.text = item.title
            binding.tvGridDeckNewValue.text = item.news.toString()
            binding.tvGridDeckLearningValue.text = item.learning.toString()
            binding.tvGridDeckReviewingValue.text = item.toReview.toString()
            binding.root.setOnClickListener {
                onClick(item.id)
            }
            binding.root.setOnLongClickListener {
                onLongClick(item.id)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DecksAdapter.ViewHolder =
        ViewHolder(
            ItemDeckBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DecksAdapter.ViewHolder, position: Int) {
        val item = currentList[position]
        if (item != null) holder.bindItems(item)
    }
}

private class DeckDiffCallback : DiffUtil.ItemCallback<DeckEntity>() {
    override fun areItemsTheSame(oldItem: DeckEntity, newItem: DeckEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: DeckEntity, newItem: DeckEntity) =
        oldItem == newItem
}