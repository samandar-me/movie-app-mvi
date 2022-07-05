package com.sdk.movieapp.presentation.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.sdk.movieapp.R
import com.sdk.movieapp.database.MovieDatabase
import com.sdk.movieapp.databinding.FragmentDetailBinding
import com.sdk.movieapp.model.Result
import com.sdk.movieapp.repository.DetailRepository
import com.sdk.movieapp.util.Constants
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var result: Result
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
    }

    private fun initViews(view: View) {
        val viewModelFactory =
            DetailViewModelFactory(DetailRepository(MovieDatabase.invoke(requireContext())))
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        result = arguments?.getParcelable("result")!!

        val image = "${Constants.BASE_IMG}${result.backdrop_path}"

        Picasso.get()
            .load(image)
            .into(binding.imageView)

        binding.textTitle.text = result.original_title
        binding.textDesc.text = "Language: ${result.original_language}\n${result.overview}"

        if (result.isSaved) {
            binding.btnDelSv.text = getString(R.string.delete)
        } else {
            binding.btnDelSv.text = getString(R.string.save)
        }
        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
        binding.btnDelSv.setOnClickListener {
            if (result.isSaved) {
                viewModel.deleteMovie(result)
                it.findNavController().popBackStack()
            } else {
                viewModel.saveMovie(result)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}