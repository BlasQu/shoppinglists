package com.example.shoppinglists.Fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglists.MainActivity
import com.example.shoppinglists.R
import com.example.shoppinglists.RecyclerView.Adapter
import kotlinx.android.synthetic.main.fragment_shopping_lists.*

class ShoppingListsFragment : Fragment(R.layout.fragment_shopping_lists) {

    lateinit var actv : MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actv = activity as MainActivity

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = Adapter()
        val divider = DividerItemDecoration(actv, DividerItemDecoration.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(actv, R.drawable.rv_decoration_line)!!)

        rv_lists.apply {
            layoutManager = LinearLayoutManager(actv)
            this.adapter = adapter
            addItemDecoration(divider)
        }

        actv.viewmodel.data.observe(actv, {
            adapter.submitData(it)
        })
    }
}

