package com.debanshu777.newsapp.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.debanshu777.newsapp.R
import com.debanshu777.newsapp.util.UserPreferences
import kotlinx.android.synthetic.main.fragment_onboarding3.*
import kotlinx.android.synthetic.main.fragment_onboarding3.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnboardingFragment3 : Fragment() {
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_onboarding3, container, false)
        view.enter.setOnClickListener {
            val text=view.editText.text.toString().trim()
            if (text == "") {
                return@setOnClickListener
            }
            GlobalScope.launch {
                // don't forget "@MainActivity"
                UserPreferences.setValue(requireContext(), "testKey", text)
            }
            GlobalScope.launch(Dispatchers.IO) {
                // don't forget "@MainActivity"
//                DataStoreManager.getStringValue(this@MainActivity, "testKey")
                // or
                val value = UserPreferences.getValue(requireContext(), "testKey", default = "123")
                withContext(Dispatchers.Main) {
                    textView.text = value
                }
            }
        }


        return view
    }
}