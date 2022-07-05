package com.sdk.movieapp.presentation.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.sdk.movieapp.R
import com.sdk.movieapp.database.MovieDatabase
import com.sdk.movieapp.databinding.FragmentFavoriteBinding
import com.sdk.movieapp.model.Result
import com.sdk.movieapp.presentation.movie.MovieAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
    }
    private fun initViews(view: View) {
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        viewModel.init(MovieDatabase.invoke(requireContext()))
        setupRv()
        viewModel.result.observe(viewLifecycleOwner) { list ->
            movieAdapter.submitList(list)
        }
        movieAdapter.onClick = {
            val result = Result(it.id, it.backdrop_path, it.original_language, it.original_title, it.overview, it.poster_path, it.vote_average,  true)
            val bundle = bundleOf("result" to result)
            view.findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    private fun setupRv() = binding.recyclerView.apply {
        movieAdapter = MovieAdapter()
        val snapHelper = PagerSnapHelper()
        adapter = movieAdapter
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        snapHelper.attachToRecyclerView(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}