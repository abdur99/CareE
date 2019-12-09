package com.example.care_e


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 */
class aboutus : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val info = inflater.inflate(R.layout.fragment_aboutus, container, false)

        info.findViewById(R.id.aboutcare) as TextView

        info.isSelected = true
        return info


    }


}
