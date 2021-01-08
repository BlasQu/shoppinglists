package com.example.shoppinglists.Fragments

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglists.MainActivity
import com.example.shoppinglists.R
import com.example.shoppinglists.RecyclerView.Adapter
import com.example.shoppinglists.RecyclerView.AdapterArchives
import com.example.shoppinglists.RecyclerView.AdapterDetails
import com.example.shoppinglists.RecyclerView.AdapterDetailsArchives
import kotlinx.android.synthetic.main.fragment_details_lists.*

class DetailsListsArchivesFragment : Fragment(R.layout.fragment_details_lists) {
    lateinit var actv : MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actv = activity as MainActivity

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = AdapterDetailsArchives(actv)
        val decoration = DividerItemDecoration(actv, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(actv.resources.getDrawable(R.drawable.rv_decoration_line))

        rv_details.apply {
            layoutManager = LinearLayoutManager(actv)
            this.adapter = adapter
            addItemDecoration(decoration)
        }

        actv.viewmodel.archivedata.observe(actv, {
            if (it.isNotEmpty()) adapter.submitData(actv.viewmodel.archivedata.value!![actv.viewmodel.currentDetails].details)
            else adapter.submitData(emptyList())
        })
    }
}
