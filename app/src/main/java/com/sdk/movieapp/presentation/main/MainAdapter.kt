package com.sdk.movieapp.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sdk.movieapp.presentation.favorite.FavoriteFragment
import com.sdk.movieapp.presentation.movie.MovieFragment
import com.sdk.movieapp.presentation.search.SearchFragment

class MainAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MovieFragment()
            1 -> SearchFragment()
            2 -> FavoriteFragment()
            else -> Fragment()
        }
    }
}