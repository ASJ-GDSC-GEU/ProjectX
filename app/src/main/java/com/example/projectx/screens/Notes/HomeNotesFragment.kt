package com.example.projectx.screens.Notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.projectx.R
import com.example.projectx.databinding.FragmentHomeNotesBinding
import com.example.projectx.screens.GetStartedFragmentDirections


class HomeNotesFragment : Fragment() {

    private var _binding : FragmentHomeNotesBinding ?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeNotesBinding.inflate(inflater, container, false)

        binding.create.setOnClickListener {
            val action = HomeNotesFragmentDirections.actionHomeNotesFragmentToCreateNoteFragment()
            requireView().findNavController().navigate(action)
        }

        val view = binding.root
        return view
    }

}