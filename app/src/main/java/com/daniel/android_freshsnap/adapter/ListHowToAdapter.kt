package com.daniel.android_freshsnap.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.android_freshsnap.api.response.DetailResponse
import com.daniel.android_freshsnap.databinding.HowToKeepBinding


class ListHowToAdapter(private val listHowTo: ArrayList<DetailResponse.DetailItem>)
    : RecyclerView.Adapter<ListHowToAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: HowToKeepBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = HowToKeepBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val result = listHowTo[position]

        val howto = result.howtokeep

        var howToList = howto
        val strings = howToList.split(",").toTypedArray()

        for (i in strings.indices) {
            strings[i] = strings[i].trim { it <= ' ' }
            strings[i] += "\n"
        }
        howToList = ""

        for (i in strings.indices) {
            howToList += String.format("%s. %s",i+1,strings[i])
        }

        holder.binding.tvHowto.text = howToList
    }

    override fun getItemCount(): Int = listHowTo.size

    @SuppressLint("NotifyDataSetChanged")
    fun setHowTo(data: List<DetailResponse.DetailItem>){
        listHowTo.clear()
        listHowTo.addAll(data)
        notifyDataSetChanged()
    }
}