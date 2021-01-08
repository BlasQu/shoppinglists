package com.example.shoppinglists.RecyclerView

import android.graphics.Color
import android.os.Build
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Placeholder
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.Fragments.DetailsListsFragment
import com.example.shoppinglists.MainActivity
import com.example.shoppinglists.R
import com.example.shoppinglists.Utils.AlertDialogs
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add_details.view.*
import kotlinx.android.synthetic.main.rv_item.view.*
import kotlinx.android.synthetic.main.rv_itemdetails.view.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class Adapter (
        val mainActivity: MainActivity
) : RecyclerView.Adapter<Adapter.VH>() {

    private var multiSelection = false
    private val selectedItems = mutableListOf<ListItem>()
    private var allSelected = false
    lateinit var observer : Observer<Int>
    lateinit var resultObserver : Observer<Boolean>

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setItemViewClickListener() {
                mainActivity.supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    replace(R.id.fragment_container, DetailsListsFragment(), "DetailsListsFragment")
                    commit()
                }
                mainActivity.viewmodel.currentDetails = adapterPosition
                mainActivity.tablayout.animation = AnimationUtils.loadAnimation(mainActivity, R.anim.alpha_in).apply {
                    interpolator = FastOutSlowInInterpolator()
                    duration = 250
                    mainActivity.tablayout.visibility = View.GONE
                }
                mainActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                mainActivity.toolbar.title = "Shopping list details"
        }
        init {

            itemView.setOnClickListener {
                if (multiSelection) itemSelect(itemView, adapterPosition)
                else {
                    setItemViewClickListener()
                }
            }
        }
    }


    private val data = mutableListOf<ListItem>()

    inner class DiffCallback(val oldList: List<ListItem>, val newList: List<ListItem>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].itemId == newList[newItemPosition].itemId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].title == newList[newItemPosition].title &&
                    oldList[oldItemPosition].itemId == newList[newItemPosition].itemId
                    )
        }
    }

    fun itemSelect(itemView: View, position: Int) {
        if (data[position] in selectedItems) {
            selectedItems.remove(data[position])
            itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.white))
        } else {
            selectedItems.add(data[position])
            itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.sky_blue2))
        }

        mainActivity.viewmodel.selectedItems2.postValue(selectedItems.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.text_list_name.text = data[position].title
        holder.itemView.text_list_desc.text = data[position].date

        holder.itemView.setOnLongClickListener {
            if (!multiSelection){
            val actionMode = object : ActionMode.Callback {
                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    mode!!.menuInflater.inflate(R.menu.menu_action, menu)
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
                        R.id.delete -> {
                            mainActivity.viewmodel.result.observe(mainActivity, {
                                if (it) {
                                    selectedItems.clear()
                                    allSelected = false
                                }
                                mainActivity.viewmodel.result.postValue(false)
                            })
                            if (selectedItems.isEmpty()) Snackbar.make(mainActivity.relLayout, "No items found to delete.", Snackbar.LENGTH_SHORT).show()
                            else AlertDialogs.dialog_confirm_delete(holder.itemView.context as MainActivity, selectedItems)
                        }

                        R.id.toArchive -> {
                            val intList = mutableListOf<Int>()
                            for (id in selectedItems) {
                                intList.add(id.itemId)
                            }
                            mainActivity.viewmodel.updateToArchive(intList)
                            selectedItems.clear()
                            allSelected = false

                            mainActivity.viewmodel.selectedItems2.postValue(selectedItems.size)
                        }

                        R.id.selectAll -> {
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
         else holder.itemView.setBackgroundColor(mainActivity.resources.getColor(R.color.white))

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