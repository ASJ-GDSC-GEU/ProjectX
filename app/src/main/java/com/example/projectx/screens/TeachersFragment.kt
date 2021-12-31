package com.example.projectx.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.projectx.R
import com.example.projectx.databinding.FragmentTeachersBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TeachersFragment : Fragment() {
    private var _binding : FragmentTeachersBinding? = null
    private val binding get() = _binding!!

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab2.setOnClickListener {
            Notes()
        }

    }

    private fun Notes(){
        val action = TeachersFragmentDirections.actionTeachersFragmentToHomeNotesFragment()
        requireView().findNavController().navigate(action)
    }
    private fun addTaskCalendarDialog(){
        MaterialAlertDialogBuilder(requireContext()).setTitle("Add Task").setMessage("Choose Date and Time")
    }

}