package com.github.llmaximll.worldcinema.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.adaptersholders.viewpager.TrendsAdapter
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.vm.MainScreenVM
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

private const val TAG = "MainScreenFragment"

private const val ARG_TOKEN = "arg_token"

class MainScreenFragment : Fragment() {

    private lateinit var viewModel: MainScreenVM
    private lateinit var cf: CommonFunctions
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var posterImageView: ImageView
    private lateinit var lastImageView: ImageView
    private lateinit var playImageView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cf = CommonFunctions.get()
        viewModel = cf.initVM(this, MainScreenVM::class.java) as MainScreenVM
        //изменение цвета статус бара на прозрачный
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_main_screen, container, false)

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
        posterImageView = view.findViewById(R.id.poster_ImageView)
        lastImageView = view.findViewById(R.id.last_imageView)
        playImageView = view.findViewById(R.id.play_ImageView)

        viewPager.adapter = TrendsAdapter(this)
        //отключение скролла viewpager
        viewPager.isUserInputEnabled = false

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabs()
        downloadInfoLastView()
        downloadInfoPoster()
    }

    override fun onStart() {
        super.onStart()
        playImageView.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    cf.animateView(view, false)
                }
                MotionEvent.ACTION_CANCEL -> {
                    cf.animateView(view, true)
                }
                MotionEvent.ACTION_UP -> {
                    cf.animateView(view, true)
                    view.performClick()
                }
            }
            true
        }
    }

    private fun setTabs() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "В тренде"
                1 -> tab.text = "Новое"
                2 -> tab.text = "Для вас"
            }
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }

    private fun downloadInfoPoster() {
        viewModel.downloadInfoPoster()
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.posterInfo.collect { posterInfo ->
                if (posterInfo != null) {
                    updatePoster("http://cinema.areas.su/up/images/${posterInfo.foregroundImage}")
                } else {
                    cf.log(TAG, "downloadInfoImages | posterInfo = null")
                }
            }
        }
    }

    private fun downloadInfoLastView() {
        val token = arguments?.getInt(ARG_TOKEN, 0) ?: 0
        cf.log(TAG, "downloadInfoLastView | token=$token")
        viewModel.downloadInfoLastView(token)
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.lastViewInfo.collect { movieInfo ->
                if (movieInfo != null) {
                    updateLastView("http://cinema.areas.su/up/images/${movieInfo.poster}")
                    cf.log(TAG, "movieInfo=$movieInfo")
                } else {
                    cf.log(TAG, "downloadInfoLastView | movieInfo = null")
                }
            }
        }
    }

    private fun updatePoster(posterURL: String) {
        //Update poster
        Glide.with(this)
            .load(posterURL)
            .centerCrop()
            .placeholder(R.drawable.logo_foreground)
            .error(R.drawable.logo_foreground)
            .fallback(R.drawable.logo_foreground)
            .into(posterImageView)
    }

    private fun updateLastView(lastViewURL: String) {
        //Update lastView
        Glide.with(this)
            .load(lastViewURL)
            .centerCrop()
            .placeholder(R.drawable.logo_foreground)
            .error(R.drawable.logo_foreground)
            .fallback(R.drawable.logo_foreground)
            .into(lastImageView)
    }

    companion object {
        fun newInstance(token: Int?): MainScreenFragment {
            val args = Bundle().apply {
                if (token != null) {
                    putInt(ARG_TOKEN, token)
                }
            }
            return MainScreenFragment().apply {
                arguments = args
            }
        }
    }
}