package com.example.shoppinglists.RecyclerView

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglists.Data.ListDetails
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.MainActivity
import com.example.shoppinglists.R
import com.example.shoppinglists.Utils.AlertDialogs
import com.google.android.gms.common.util.ArrayUtils.removeAll
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add_details.view.*
import kotlinx.android.synthetic.main.rv_item.view.*
import kotlinx.android.synthetic.main.rv_itemdetails.view.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class AdapterDetails (
        val mainActivity: MainActivity
) : RecyclerView.Adapter<AdapterDetails.VH>() {

    private var multiSelection = false
    private val selectedItems = mutableListOf<ListDetails>()
    private var allSelected = false
    lateinit var observer : Observer<Int>
    lateinit var resultObserver : Observer<Boolean>

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setItemViewClickListener(itemView: View, adapterPosition: Int) {
            if (data[adapterPosition].done) {
                itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.white))
                itemView.mark.setImageDrawable(mainActivity.resources.getDrawable(R.drawable.ic_baseline_check_circle_outline_24))
                itemView.text_item_name.paintFlags = 0
                itemView.text_item_amount.paintFlags = 0

                data[adapterPosition].done = false
                var currentItem = mainActivity.viewmodel.data.value!![mainActivity.viewmodel.currentDetails]
                currentItem.details = data
                mainActivity.viewmodel.updateList(currentItem)
            } else {
                itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.sky_green))
                itemView.mark.setImageDrawable(mainActivity.resources.getDrawable(R.drawable.ic_baseline_check_circle_24))
                itemView.text_item_name.paintFlags = (Paint.STRIKE_THRU_TEXT_FLAG)
                itemView.text_item_amount.paintFlags = (Paint.STRIKE_THRU_TEXT_FLAG)

                data[adapterPosition].done = true
                var currentItem = mainActivity.viewmodel.data.value!![mainActivity.viewmodel.currentDetails]
                currentItem.details = data
                mainActivity.viewmodel.updateList(currentItem)
            }
    }
        init {
                itemView.setOnClickListener {
                    if (multiSelection) itemSelect(itemView, adapterPosition)
                    else setItemViewClickListener(itemView, adapterPosition)
                }
            }
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
            return (oldList[oldItemPosition].item == newList[newItemPosition].item &&
                    oldList[oldItemPosition].detailsId == newList[newItemPosition].detailsId
                    )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.rv_itemdetails, parent, false))
    }

    fun itemSelect(itemView: View, position: Int) {
        if (data[position] in selectedItems) {
            selectedItems.remove(data[position])
            if (itemView.text_item_name.paintFlags == 0) itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.white))
            else itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.sky_green))
        } else {
            selectedItems.add(data[position])
            itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.sky_blue2))
        }

        mainActivity.viewmodel.selectedItems2.postValue(selectedItems.size)
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
            holder.itemView.text_item_name.paintFlags = 0
            holder.itemView.text_item_amount.paintFlags = 0
            holder.itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.white))
            holder.itemView.mark.setImageDrawable(mainActivity.resources.getDrawable(R.drawable.ic_baseline_check_circle_outline_24))
            }

        holder.itemView.setOnLongClickListener {
            if (!multiSelection){
                val actionMode = object : ActionMode.Callback {
                    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        mode!!.menuInflater.inflate(R.menu.menu_action_details, menu)
                        return true
                    }

                    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                        TabLayoutUtils.enableTabs(mainActivity.tablayout, false)
                        multiSelection = true
                        mainActivity.viewmodel.result.postValue(false)
                        itemSelect(holder.itemView, holder.adapterPosition)

                        observer = Observer<Int>() {
                            mode!!.title = "Selected items: $it"
                        }

                        resultObserver = Observer<Boolean>() {
                            if (it == true) selectedItems.clear()
                        }

                        mainActivity.viewmodel.selectedItems2.observe(mainActivity, observer)
                        return true
                    }

                    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                        when (item!!.itemId) {
                            R.id.deleteDetails -> {

                                if (selectedItems.isEmpty()) Snackbar.make(mainActivity.relLayout, "No items found to delete.", Snackbar.LENGTH_SHORT).show()
                                else {
                                    var currentItem = mainActivity.viewmodel.data.value!![mainActivity.viewmodel.currentDetails]
                                    val newData: MutableList<ListDetails> = mutableListOf()
                                    newData.addAll(data)
                                    for (selectedItem in selectedItems) {
                                        newData.remove(selectedItem)
                                    }
                                    val newData2: MutableList<ListDetails> = mutableListOf()
                                    newData2.addAll(newData)
                                    currentItem.details = newData2

                                    mainActivity.viewmodel.updateList(currentItem)

                                    selectedItems.clear()
                                    allSelected = false
                                    mainActivity.viewmodel.selectedItems2.postValue(0)
                                }
                            }

                            R.id.selectAllDetails -> {
                                if (selectedItems.size == data.size) {
                                    allSelected = false
                                    selectedItems.clear()
                                } else {
                                    allSelected = true
                                    selectedItems.clear()
                                    selectedItems.addAll(data)
                                }

                                notifyDataSetChanged()
                                mainActivity.viewmodel.selectedItems2.postValue(selectedItems.size)
                            }
                        }
                        return true
                    }

                    override fun onDestroyActionMode(mode: ActionMode?) {
                        multiSelection = false
                        allSelected = false
                        selectedItems.clear()
                        mainActivity.viewmodel.result.removeObserver(resultObserver)
                        mainActivity.viewmodel.selectedItems2.removeObserver(observer)
                        notifyDataSetChanged()
                        TabLayoutUtils.enableTabs(mainActivity.tablayout, true)
                    }
                }
                mainActivity.startActionMode(actionMode)
            }
            return@setOnLongClickListener true
        }

        if (allSelected) holder.itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.sky_blue2))
        else {
            if (holder.itemView.text_item_name.paintFlags == 0) holder.itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.white))
            else holder.itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.sky_green))
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