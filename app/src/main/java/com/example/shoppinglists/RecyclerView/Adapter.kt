package com.example.shoppinglists.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.R
import kotlinx.android.synthetic.main.rv_item.view.*

class Adapter : RecyclerView.Adapter<Adapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val data = mutableListOf<ListItem>()

    inner class DiffCallback(val oldList: List<ListItem>, val newList: List<ListItem>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title == newList[newItemPosition].title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.text_list_name.text = data[position].title
        holder.itemView.text_list_desc.text = data[position].date
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitData(newList: List<ListItem>) {
        val DiffResult = DiffUtil.calculateDiff(DiffCallback(data, newList))
        data.clear()
        data.addAll(newList)
        DiffResult.dispatchUpdatesTo(this)
    }
}