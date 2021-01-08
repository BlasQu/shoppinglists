package com.example.shoppinglists.Utils

import com.example.shoppinglists.MainActivity

object checkCurrentFragment {
    fun checkFragment(context: MainActivity) : Int {
        val fragment = context.supportFragmentManager.findFragmentByTag("ShoppingListsFragment")
        val fragment2 = context.supportFragmentManager.findFragmentByTag("DetailsListsFragment")
        if (fragment != null && fragment.isVisible) {
            return 1
        } else if (fragment2 != null && fragment2.isVisible) {
            return 2
        }
        return 0
    }
}