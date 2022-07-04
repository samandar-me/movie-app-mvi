package com.sdk.movieapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import com.sdk.movieapp.R
import com.sdk.movieapp.databinding.FragmentMainBinding
import com.sdk.movieapp.presentation.MainActivity

class MainFragment : Fragment(), NavigationBarView.OnItemSelectedListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {
        mainAdapter = MainAdapter(this)
        binding.viewPager.adapter = mainAdapter
        binding.bottomNav.setOnItemSelectedListener(this)
        binding.viewPager.isUserInputEnabled = false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_movie -> changeTitle(0, R.string.movies)
            R.id.men_search -> changeTitle(1, R.string.search)
            R.id.menu_fav -> changeTitle(2, R.string.fav)
        }
        return true
    }
    private fun changeTitle(index: Int, title: Int) {
        binding.viewPager.currentItem = index
        (activity as MainActivity).supportActionBar?.title = getString(title)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}