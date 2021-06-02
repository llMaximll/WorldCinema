package com.github.llmaximll.worldcinema.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.common.CommonFunctions

private const val TAG = "MovieScreenFragment"
private const val ARG_MOVIE_ID = "arg_movie_id"

class MovieScreenFragment : Fragment() {

    private lateinit var cf: CommonFunctions
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cf = CommonFunctions.get()
        movieId = arguments?.getInt(ARG_MOVIE_ID, 0) ?: 0
        cf.log(TAG, "movieId=$movieId")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_movie_screen, container, false)



        return view
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