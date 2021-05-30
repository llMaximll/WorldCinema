package com.github.llmaximll.worldcinema.fragments

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.vm.LaunchScreenVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "LaunchScreenFragment"
private const val TIMEOUT = 1_000L

class LaunchScreenFragment : Fragment() {

    interface Callbacks {
        fun onLaunchScreenFragment(fragmentNumber: Int, sharedElement1: View)
    }

    private lateinit var viewModel: LaunchScreenVM
    private lateinit var cf: CommonFunctions
    private lateinit var logoImageView: View
    private var callbacks: Callbacks? = null
    private var firstLaunch = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cf = CommonFunctions.get()
        viewModel = cf.initVM(this, LaunchScreenVM::class.java)
                as LaunchScreenVM
        viewModel.startNextScreen(TIMEOUT)
        collectState()
        firstLaunch = viewModel.checkFirstLaunch(requireContext())
        //fragment transition
        exitTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_launch_screen, container, false)

        logoImageView = view.findViewById(R.id.imageView)

        return view
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun collectState() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.state.collect {
                if (it) {
                    if (firstLaunch)
                        callbacks?.onLaunchScreenFragment(1, logoImageView)
                    else
                        callbacks?.onLaunchScreenFragment(0, logoImageView)
                }
            }
        }
    }

    companion object {
        fun newInstance(): LaunchScreenFragment = LaunchScreenFragment()
    }
}