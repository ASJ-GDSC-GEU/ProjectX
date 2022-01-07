package com.example.projectx.screens.Student

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.projectx.R
import com.example.projectx.daos.TopDao
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

        binding.apply {
            activity?.setActionBar(toolbar2)
            activity?.actionBar?.setDisplayShowTitleEnabled(false)
            joinMeet.setOnClickListener {
                val action = StudentFragmentDirections.actionStudentFragmentToMeetingHome()
                requireView().findNavController().navigate(action)
            }
            studentFloat.setOnClickListener {
                popUpMenu(view)
            }

            val userImage = TopDao().currentUser().photoUrl
            Glide.with(view.context).load(userImage).circleCrop().error(R.drawable.user_error).into(userLogo)
        }

        return view
    }

    private fun popUpMenu(view: View) {
        val popupMenu: PopupMenu =
            PopupMenu(context, binding.studentFloat, Gravity.END, 0, R.style.MyPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.student_fabmenu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.joinClass_menu ->
                    navigateToJoinClass()
                R.id.assignment_menu ->
                    navigateToStuAssignment()
                R.id.notes_menu ->
                    navigateToStuNotes()
            }
            true
        })
        popupMenu.show()
    }

    private fun navigateToJoinClass() {
        val action = StudentFragmentDirections.actionStudentFragmentToJoinClassFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToStuNotes() {
        val action = StudentFragmentDirections.actionStudentFragmentToHomeNotesFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToStuAssignment() {
        val action = StudentFragmentDirections.actionStudentFragmentToStudentAssignment()
        requireView().findNavController().navigate(action)
    }


}