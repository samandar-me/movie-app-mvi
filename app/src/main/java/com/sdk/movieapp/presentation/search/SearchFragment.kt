package com.sdk.movieapp.presentation.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.sdk.movieapp.R
import com.sdk.movieapp.databinding.FragmentSearchBinding
import com.sdk.movieapp.presentation.movie.MainState
import com.sdk.movieapp.presentation.movie.MovieAdapter
import com.sdk.movieapp.util.log
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
    }

    private fun initViews(view: View) {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        setupRv()
        observe()
        var job: Job? = null
        binding.editSearch.addTextChangedListener { editable: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(600L)
                editable?.let {
                    viewModel.searchUser(it.toString().trim())
                }
            }
        }
        movieAdapter.onClick = {
            val bundle = bundleOf("result" to it)
            view.findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is SearchState.Init -> Unit
                    is SearchState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.recyclerView.isVisible = false
                    }
                    is SearchState.Error -> {
                        binding.progressBar.isVisible = false
                        log(it.error.toString())
                    }
                    is SearchState.MoviesData -> {
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = true
                        movieAdapter.submitList(it.movies.results)
                    }
                }
            }
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