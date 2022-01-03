package com.debanshu777.newsFlix.ui.onboarding.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.debanshu777.newsFlix.R
import com.debanshu777.newsFlix.util.UserPreferences
import kotlinx.android.synthetic.main.fragment_onboarding3.*
import kotlinx.android.synthetic.main.fragment_onboarding3.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class OnboardingFragment3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboarding3, container, false)
        view.enter.setOnClickListener {
            val text = view.editText.text.toString().trim()
            if (text == "") {
                return@setOnClickListener
            }
            GlobalScope.launch {
                UserPreferences.setValue(requireContext(), "nameKey", text)
            }
            view.enter.hideKeyboard()
            runBlocking {
                delay(1200)
            }
            findNavController().navigate(R.id.action_viewPagerFragment_to_breakingNewsFragment)
        }

        return view
    }
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
