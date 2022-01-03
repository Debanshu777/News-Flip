package com.debanshu777.newsapp.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.debanshu777.newsapp.ui.onboarding.screens.OnboardingFragment1
import com.debanshu777.newsapp.ui.onboarding.screens.OnboardingFragment2
import com.debanshu777.newsapp.ui.onboarding.screens.OnboardingFragment3

class LiquidSwipePageAdapter(fm: FragmentManager, behaviour: Int) :
    FragmentStatePagerAdapter(fm, behaviour) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return OnboardingFragment1()
            1 -> return OnboardingFragment2()
            2 -> return OnboardingFragment3()
        }
        return null!!
    }
}
