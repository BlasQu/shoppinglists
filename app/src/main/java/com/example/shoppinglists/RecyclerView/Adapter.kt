package com.example.shoppinglists.RecyclerView

import android.graphics.Color
import android.os.Build
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglists.Data.ListItem
import com.example.shoppinglists.Fragments.DetailsListsFragment
import com.example.shoppinglists.MainActivity
import com.example.shoppinglists.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.rv_item.view.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class Adapter (
        val mainActivity: MainActivity
) : RecyclerView.Adapter<Adapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
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