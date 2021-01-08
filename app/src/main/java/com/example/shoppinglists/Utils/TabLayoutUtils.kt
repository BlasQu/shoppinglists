
import android.view.View
import android.view.ViewGroup

import com.google.android.material.tabs.TabLayout


object TabLayoutUtils {

    fun enableTabs(tabLayout: TabLayout?, enable: Boolean) {
        val viewGroup = tabLayout?.getChildAt(0) as ViewGroup
        for (childIndex in 0 until viewGroup.childCount) {
            viewGroup.getChildAt(childIndex)?.isEnabled = enable
        }
    }
}