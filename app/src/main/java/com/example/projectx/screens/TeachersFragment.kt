package com.example.projectx.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectx.R
import com.example.projectx.databinding.FragmentTeachersBinding

class TeachersFragment : Fragment() {
    private var _binding : FragmentTeachersBinding? = null
    private val binding = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentTeachersBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}