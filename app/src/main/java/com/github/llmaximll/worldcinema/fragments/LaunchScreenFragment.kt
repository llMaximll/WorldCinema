package com.github.llmaximll.worldcinema.fragments

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.llmaximll.worldcinema.R
import com.github.llmaximll.worldcinema.common.CommonFunctions
import com.github.llmaximll.worldcinema.vm.LaunchScreenVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "LaunchScreenFragment"
private const val TIMEOUT = 3_000L

class LaunchScreenFragment : Fragment() {

    interface Callbacks {
        fun onLaunchScreenFragment(sharedElement1: View)
    }

    private lateinit var viewModel: LaunchScreenVM
    private lateinit var commonFunctions: CommonFunctions
    private lateinit var logoImageView: View
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        commonFunctions = CommonFunctions()
        viewModel = commonFunctions.initVM(this, LaunchScreenVM::class.java)
                as LaunchScreenVM
        viewModel.startNextScreen(TIMEOUT)
        collectState()
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
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.state.collect {
                if (it) {
                    commonFunctions.log(TAG, "collectState")
                    callbacks?.onLaunchScreenFragment(logoImageView)
                }
            }
        }
    }

    companion object {
        fun newInstance(): LaunchScreenFragment = LaunchScreenFragment()
    }
}