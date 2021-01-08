package com.example.shoppinglists.Fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglists.MainActivity
import com.example.shoppinglists.R
import com.example.shoppinglists.RecyclerView.Adapter
import com.example.shoppinglists.RecyclerView.AdapterArchives
import kotlinx.android.synthetic.main.fragment_archived_lists.*
import kotlinx.android.synthetic.main.fragment_shopping_lists.*
import kotlinx.android.synthetic.main.fragment_shopping_lists.rv_lists

class ArchivedListsFragment : Fragment(R.layout.fragment_archived_lists) {

    lateinit var actv : MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actv = activity as MainActivity

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = AdapterArchives(actv)
        val divider = DividerItemDecoration(actv, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(actv, R.drawable.rv_decoration_line)!!)

        rv_lists.apply {
            layoutManager = LinearLayoutManager(actv)
            this.adapter = adapter
            addItemDecoration(divider)
        }

        actv.viewmodel.archivedata.observe(actv, {
            adapter.submitData(it)
        })
    }
}