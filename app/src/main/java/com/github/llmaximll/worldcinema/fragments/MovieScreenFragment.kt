package com.github.llmaximll.worldcinema.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.adaptersholders.recyclerview.FramesAdapter
import com.github.llmaximll.worldcinema.adaptersholders.recyclerview.TagsAdapter
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.dataclasses.network.MovieInfo
import com.github.llmaximll.worldcinema.vm.MovieScreenVM
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "MovieScreenFragment"
private const val ARG_MOVIE_ID = "arg_movie_id"

class MovieScreenFragment : Fragment() {

    private lateinit var cf: CommonFunctions
    private lateinit var viewModel: MovieScreenVM
    private lateinit var posterImageView: ImageView
    private lateinit var toolBar: MaterialToolbar
    private lateinit var titleTextView: TextView
    private lateinit var tagsRecyclerView: RecyclerView
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var descriptionTextView: TextView
    private lateinit var framesRecyclerView: RecyclerView
    private var movieId: String = "movieId=null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cf = CommonFunctions.get()
        viewModel = cf.initVM(this, MovieScreenVM::class.java) as MovieScreenVM
        movieId = arguments?.getString(ARG_MOVIE_ID, "movieId=null") ?: "movieId=null"
        cf.log(TAG, "movieId=$movieId")
        //return status bar
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_movie_screen, container, false)

        posterImageView = view.findViewById(R.id.poster_ImageView)
        toolBar = view.findViewById(R.id.toolBar)
        titleTextView = view.findViewById(R.id.toolBar_textView)
        tagsRecyclerView = view.findViewById(R.id.tags_recyclerView)
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout)
        descriptionTextView = view.findViewById(R.id.description_textView)
        framesRecyclerView = view.findViewById(R.id.frames_recyclerView)

        setupRecyclerViews()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar()
        downloadInfoRequiredMovie(movieId)
    }

    private fun downloadInfoRequiredMovie(movieId: String) {
        viewModel.downloadInfoRequiredMovie(movieId)
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.movieInfo.collect { movieInfo ->
                if (movieInfo != null) {
                    updateUI(movieInfo)
                    cf.log(TAG, "downloadInfoRequiredMovie | movieInfo=$movieInfo")
                } else {
                    cf.log(TAG, "downloadInfoRequiredMovie | movieInfo = null")
                }
            }
        }
    }

    private fun setupRecyclerViews() {
        //tags
        tagsRecyclerView.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.HORIZONTAL)
        tagsRecyclerView.adapter = TagsAdapter(listOf())
        //frames
        framesRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)
        framesRecyclerView.adapter = FramesAdapter(listOf())
    }

    private fun updateUI(movieInfo: MovieInfo) {
        Glide.with(this)
            .load("http://cinema.areas.su/up/images/${movieInfo.poster}")
            .centerCrop()
            .placeholder(R.drawable.logo_foreground)
            .error(R.drawable.logo_foreground)
            .fallback(R.drawable.logo_foreground)
            .into(posterImageView)
        //toolBar
        titleTextView.text = movieInfo.name
        when (movieInfo.age) {
            "18" -> {
                toolBar.menu.findItem(R.id.age).setIcon(R.drawable.ic_age_18)
            }
            "16" -> {
                toolBar.menu.findItem(R.id.age).setIcon(R.drawable.ic_age_16)
            }
            "12" -> {
                toolBar.menu.findItem(R.id.age).setIcon(R.drawable.ic_age_12)
            }
            "6" -> {
                toolBar.menu.findItem(R.id.age).setIcon(R.drawable.ic_age_6)
            }
            "0" -> {
                toolBar.menu.findItem(R.id.age).setIcon(R.drawable.ic_age_0)
            }
        }
        toolBar.menu.findItem(R.id.comments).setIcon(R.drawable.ic_messages)
        //tagsRecyclerView
        tagsRecyclerView.adapter = TagsAdapter(movieInfo.tags)
        //descriptionTextView
        descriptionTextView.text = movieInfo.description
        //framesRecyclerView
        framesRecyclerView.adapter = FramesAdapter(movieInfo.images)
    }

    private fun setToolBar() {
        toolBar.setNavigationIcon(R.drawable.ic_back_arrow)
        toolBar.setNavigationIconTint(Color.WHITE)
        toolBar.setTitleTextColor(Color.WHITE)
        toolBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance(movieId: String): MovieScreenFragment {
            val args = Bundle().apply {
                putString(ARG_MOVIE_ID, movieId)
            }
            return MovieScreenFragment().apply {
                arguments = args
            }
        }
    }
}