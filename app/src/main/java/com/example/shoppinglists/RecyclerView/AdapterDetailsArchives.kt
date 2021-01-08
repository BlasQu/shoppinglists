package com.example.shoppinglists.RecyclerView

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglists.Data.ListDetails
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.MainActivity
import com.example.shoppinglists.R
import kotlinx.android.synthetic.main.rv_item.view.*
import kotlinx.android.synthetic.main.rv_itemdetails.view.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class AdapterDetailsArchives (
        val mainActivity: MainActivity
) : RecyclerView.Adapter<AdapterDetailsArchives.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    private val data = mutableListOf<ListDetails>()

    inner class DiffCallback(val oldList: List<ListDetails>, val newList: List<ListDetails>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].detailsId == newList[newItemPosition].detailsId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].item == newList[newItemPosition].item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.rv_itemdetails, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.text_item_name.text = data[position].item
        holder.itemView.text_item_amount.text = ("X${data[position].amount}")
        if (data[position].done) {
            holder.itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.sky_green))
            holder.itemView.text_item_name.paintFlags = (Paint.STRIKE_THRU_TEXT_FLAG)
            holder.itemView.text_item_amount.paintFlags = (Paint.STRIKE_THRU_TEXT_FLAG)
            holder.itemView.mark.setImageDrawable(mainActivity.resources.getDrawable(R.drawable.ic_baseline_check_circle_24))
        }
        else {
            holder.itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.white))
            holder.itemView.text_item_name.paintFlags = 0
            holder.itemView.text_item_amount.paintFlags = 0
            holder.itemView.mark.setImageDrawable(mainActivity.resources.getDrawable(R.drawable.ic_baseline_check_circle_outline_24))
            }
        }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitData(newList: List<ListDetails>) {
        val DiffResult = DiffUtil.calculateDiff(DiffCallback(data, newList))
        data.clear()
        data.addAll(newList)
        DiffResult.dispatchUpdatesTo(this)
    }
}