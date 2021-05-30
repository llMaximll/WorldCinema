package com.github.llmaximll.worldcinema

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.llmaximll.worldcinema.fragments.LaunchScreenFragment
import com.github.llmaximll.worldcinema.fragments.MainScreenFragment
import com.github.llmaximll.worldcinema.fragments.SignInFragment
import com.github.llmaximll.worldcinema.fragments.SignUpFragment

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(),
    LaunchScreenFragment.Callbacks,
    SignUpFragment.Callbacks {

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

    override fun onLaunchScreenFragment(sharedElement1: View) {
        val fragment = SignUpFragment.newInstance()
        //fragment transition
        fragment.sharedElementEnterTransition = TransitionInflater.from(this)
            .inflateTransition(android.R.transition.move)
        fragment.enterTransition = TransitionInflater.from(this)
            .inflateTransition(android.R.transition.fade)

        supportFragmentManager
            .beginTransaction()
            .addSharedElement(sharedElement1, "tran")
            .replace(R.id.container_fragment, fragment)
            .commit()
    }

    override fun onSignUpFragment(fragment: Int) {
        val mFragment = when (fragment) {
            0 -> MainScreenFragment.newInstance()
            1 -> SignInFragment.newInstance()
            else -> MainScreenFragment.newInstance()
        }
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.animator.transition_enter_1,
                R.animator.transition_exit_1)
            .replace(R.id.container_fragment, mFragment)
            .commit()
    }
}