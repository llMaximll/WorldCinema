package com.github.llmaximll.worldcinema.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.adaptersholders.recyclerview.TrendsAdapter
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.vm.ViewPagerMainScreenTrendsVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "ViewPagerMainScreenTrendsFragment"
private const val ARG_FRAGMENT = "arg_fragment"

class ViewPagerMainScreenTrendsFragment : Fragment() {

    private lateinit var cf: CommonFunctions
    private lateinit var viewModel: ViewPagerMainScreenTrendsVM
    private lateinit var recyclerView: RecyclerView
    private var posterList: MutableList<String> = mutableListOf()
    private var countFragment = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //arguments
        countFragment = arguments?.getInt(ARG_FRAGMENT, -1) ?: -1

        cf = CommonFunctions.get()
        viewModel = cf.initVM(this, ViewPagerMainScreenTrendsVM::class.java)
                as ViewPagerMainScreenTrendsVM
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager_main_trends, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,
            false)
        recyclerView.adapter = TrendsAdapter(listOf())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        downloadInfoMovies()
    }

    private fun downloadInfoMovies() {
        if (countFragment != -1) {
            viewModel.downloadInfoMovies(countFragment)
        } else {
            cf.log(TAG, "countFragment=-1")
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.movieInfo.collect { list ->
                if (list != null && list.isNotEmpty()) {
                    list.forEach { movieInfo ->
                        posterList.add(movieInfo.poster)
                    }
                    recyclerView.adapter = TrendsAdapter(posterList)
                    cf.log(TAG, "downloadInfoImages | posterList=$posterList")
                } else {
                    cf.log(TAG, "downloadInfoImages | List<MovieInfo> = null")
                }
            }
        }
    }

    companion object {
        fun newInstance(position: Int): ViewPagerMainScreenTrendsFragment {
            val args = Bundle().apply {
                putInt(ARG_FRAGMENT, position)
            }
            return ViewPagerMainScreenTrendsFragment().apply {
                arguments = args
            }
        }
    }
}