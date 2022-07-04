package com.sdk.movieapp.presentation.movie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.sdk.movieapp.R
import com.sdk.movieapp.databinding.FragmentMovieBinding
import com.sdk.movieapp.util.log
import kotlinx.coroutines.launch

class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
    }

    private fun initViews(view: View) {
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userIntent.send(MainIntent.FetchMovies)
        }
        setupRv()
        observe()
        movieAdapter.onClick = {
            val bundle = bundleOf("result" to it)
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

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainState.Init -> Unit
                    is MainState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.recyclerView.isVisible = false
                    }
                    is MainState.Error -> {
                        binding.progressBar.isVisible = false
                        log(it.error.toString())
                    }
                    is MainState.MoviesData -> {
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = true
                        movieAdapter.submitList(it.movies.results)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}