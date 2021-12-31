package com.example.projectx.screens

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.projectx.R
import com.example.projectx.databinding.FragmentTeachersBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TeachersFragment : Fragment() {
    private var _binding: FragmentTeachersBinding? = null
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
//        binding.fab2.setOnClickListener {
//            Notes()
//        }

        //popupMenu
        binding.create.setOnClickListener {
            val popupMenu: PopupMenu =
                PopupMenu(context, binding.create, Gravity.END, 0, R.style.MyPopupMenu)
            popupMenu.menuInflater.inflate(R.menu.create_fabmenu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.create_newClass ->
                        Toast.makeText(view.context, "New Class", Toast.LENGTH_SHORT).show()
                    R.id.create_meet ->
                        Toast.makeText(view.context, "Create Meet", Toast.LENGTH_SHORT).show()
                    R.id.create_timeTable ->
                        findNavController().navigate(R.id.homeNotesFragment)
                }
                true
            })
            popupMenu.show()
        }

    }

//    private fun Notes() {
//        val action = TeachersFragmentDirections.actionTeachersFragmentToHomeNotesFragment()
//        requireView().findNavController().navigate(action)
//    }

    private fun addTaskCalendarDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Add Task")
            .setMessage("Choose Date and Time")
    }

}