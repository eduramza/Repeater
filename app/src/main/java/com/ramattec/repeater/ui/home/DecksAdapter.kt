package com.ramattec.repeater.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramattec.domain.model.deck.Deck
import com.ramattec.repeater.databinding.ItemDeckBinding

class DecksAdapter(
    private val onClick: (deckId: String) -> Unit,
    private val onLongClick: (deckId: String) -> Unit
) : ListAdapter<Deck, DecksAdapter.ViewHolder>(DeckDiffCallback()) {

    inner class ViewHolder(private val binding: ItemDeckBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: Deck) {
            binding.tvDeckTitle.text = item.title
            binding.tvGridDeckNewValue.text = item.deckCardsInfo?.news.toString()
            binding.tvGridDeckLearningValue.text = item.deckCardsInfo?.learning.toString()
            binding.tvGridDeckReviewingValue.text = item.deckCardsInfo?.reviewing.toString()
            binding.root.setOnClickListener {
                onClick(item.deckId)
            }
            binding.root.setOnLongClickListener {
                onLongClick(item.deckId)
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

private class DeckDiffCallback : DiffUtil.ItemCallback<Deck>() {
    override fun areItemsTheSame(oldItem: Deck, newItem: Deck) =
        oldItem.deckId == newItem.deckId

    override fun areContentsTheSame(oldItem: Deck, newItem: Deck) =
        oldItem == newItem
}