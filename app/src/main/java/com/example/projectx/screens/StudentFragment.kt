package com.example.projectx.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.projectx.databinding.FragmentStudentBinding


class StudentFragment : Fragment() {

    private var _binding: FragmentStudentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        val view = binding!!.root

        binding.notesButton.setOnClickListener {
            val action = StudentFragmentDirections.actionStudentFragmentToHomeNotesFragment()
            requireView().findNavController().navigate(action)
        }

        binding.joinMeet.setOnClickListener {
            val action = StudentFragmentDirections.actionStudentFragmentToMeetingHome()
            requireView().findNavController().navigate(action)
        }

        return view
    }



}