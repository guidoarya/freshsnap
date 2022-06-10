package com.daniel.android_freshsnap.adapter
/*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daniel.android_freshsnap.data.Fruits
import com.daniel.android_freshsnap.databinding.HistoryRowBinding
import com.example.storyapp.stories.HistoryDiffUtils
import java.util.ArrayList

//contoh
class ListHistoryAdapter(private val listFruit: ArrayList<Fruits>): RecyclerView.Adapter<ListHistoryAdapter.ViewHolder>() {

    private var oldStoryItem = emptyList<Fruits>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(HistoryRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldStoryItem[position])
    }

    override fun getItemCount(): Int = oldStoryItem.size

    class ViewHolder(private val binding: HistoryRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listItem: Fruits){
            Glide.with(itemView)
                .load(listItem.photo_fruit)
                .into(binding.imgPhoto)

            binding.name.text = listItem.name_fruit
        }
    }

    fun setData(newStoryItem: List<Fruits>) {
        val diffUtil = HistoryDiffUtils(oldStoryItem, newStoryItem)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldStoryItem = newStoryItem
        diffResult.dispatchUpdatesTo(this)
    }
}*/