package com.github.llmaximll.worldcinema.adaptersholders.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.llmaximll.worldcinema.fragments.ViewPagerMainScreenTrendsFragment

private const val COUNT_PAGES = 3

class TrendsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = COUNT_PAGES

    override fun createFragment(position: Int): Fragment {
        return ViewPagerMainScreenTrendsFragment.newInstance(position)
    }
}