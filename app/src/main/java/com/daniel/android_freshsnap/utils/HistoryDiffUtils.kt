package com.example.storyapp.stories

import androidx.recyclerview.widget.DiffUtil
import com.daniel.android_freshsnap.data.Fruits


//contoh
class HistoryDiffUtils(
    private val oldList: List<Fruits>,
    private val newList: List<Fruits>
): DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size


    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name_fruit == newList[newItemPosition].name_fruit

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}