package com.github.llmaximll.worldcinema

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.github.llmaximll.worldcinema.fragments.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(),
    LaunchScreenFragment.Callbacks,
    SignUpFragment.Callbacks,
    SignInFragment.Callbacks,
    MainScreenFragment.Callbacks,
    ViewPagerMainScreenTrendsFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager
            .findFragmentById(R.id.container_fragment)
        if (currentFragment == null) {
            val fragment = LaunchScreenFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container_fragment, fragment)
                .commit()
        }
    }

    override fun onLaunchScreenFragment(fragmentNumber: Int, sharedElement1: View) {
        val fragment = when (fragmentNumber) {
            0 -> SignUpFragment.newInstance()
            1 -> SignInFragment.newInstance()
            else -> SignUpFragment.newInstance()
        }
        //fragment transition
        fragment.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(android.R.transition.move)
        fragment.enterTransition = TransitionInflater.from(this)
            .inflateTransition(android.R.transition.fade)

        supportFragmentManager
            .beginTransaction()
            .addSharedElement(sharedElement1, getString(R.string.shared_element_launch_screen_logo))
            .replace(R.id.container_fragment, fragment)
            .commit()
    }

    override fun onSignUpFragment(token: Int?, fragment: Int) {
        when (fragment) {
            0 -> {
                val mFragment = MainScreenFragment.newInstance(token)
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                    .replace(R.id.container_fragment, mFragment)
                    .commit()
            }
            1 -> {
                val mFragment = SignInFragment.newInstance()
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out)
                    .replace(R.id.container_fragment, mFragment)
                    .commit()
            }
        }
    }

    override fun onSignInFragment(token: Int?, fragmentNumber: Int) {
        when (fragmentNumber) {
            0 -> {
                val fragment = MainScreenFragment.newInstance(token)
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out
                    )
                    .replace(R.id.container_fragment, fragment)
                    .commit()
            }
            1 -> {
                val fragment = SignUpFragment.newInstance()
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .setCustomAnimations(
                        android.R.animator.fade_in,
                        android.R.animator.fade_out
                    )
                    .replace(R.id.container_fragment, fragment)
                    .commit()
            }
        }
    }

    override fun onMainScreenFragment(movieId: String) {
        val fragment = MovieScreenFragment.newInstance(movieId)
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container_fragment, fragment)
            .commit()
    }

    override fun onViewPagerMainScreenTrendsFragment(movieId: String) {
        val fragment = MovieScreenFragment.newInstance(movieId)
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container_fragment, fragment)
            .commit()
    }
}