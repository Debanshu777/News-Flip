package com.debanshu777.newsapp.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.debanshu777.newsapp.R
import com.debanshu777.newsapp.onboarding.screens.OnboardingFragment1
import com.debanshu777.newsapp.onboarding.screens.OnboardingFragment2
import com.debanshu777.newsapp.onboarding.screens.OnboardingFragment3
import kotlinx.android.synthetic.main.fragment_view_pager.view.*


class ViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList= arrayListOf<Fragment>(
            OnboardingFragment1(),
            OnboardingFragment2(),
            OnboardingFragment3()
        )

        val adapter=ViewPagerAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)
        val adapter1=LiquidSwipePageAdapter(requireActivity().supportFragmentManager,1)
        view.viewPager.adapter=adapter1
        return view
    }

}