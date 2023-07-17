package com.attackervedo.webt.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.attackervedo.webt.MainActivity
import com.attackervedo.webt.fragment.WebViewFragment

class ViewPagerAdapter(private val mainActivity:MainActivity) : FragmentStateAdapter(mainActivity) {

    override fun getItemCount(): Int {
     return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                return WebViewFragment(position).apply {
                    listener = mainActivity
                }
            }
            1 -> {
                return WebViewFragment(position).apply {
                    listener = mainActivity
                }
            }
            else -> {
                return WebViewFragment(position).apply {
                    listener = mainActivity
                }
            }
        }
    }
}