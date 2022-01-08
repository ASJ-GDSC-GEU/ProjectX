package com.example.projectx.screens

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.projectx.R
import com.example.projectx.databinding.FragmentStudentBinding
import com.google.firebase.auth.FirebaseAuth


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

        val toolbar: androidx.appcompat.widget.Toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.title = "Student Area..."
        setHasOptionsMenu(true)

        binding.notesButton.setOnClickListener {
            val action = StudentFragmentDirections.actionStudentFragmentToHomeNotesFragment()
            requireView().findNavController().navigate(action)
        }

        binding.joinMeet.setOnClickListener {
            val action = StudentFragmentDirections.actionStudentFragmentToMeetingHome()
            requireView().findNavController().navigate(action)
        }

        binding.assignmentButton.setOnClickListener {
            val action = StudentFragmentDirections.actionStudentFragmentToStudentAssignment()
            requireView().findNavController().navigate(action)
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.change_role_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuDeleteNote){

        }
        when(item.itemId){
            R.id.change_role -> {
                val action = StudentFragmentDirections.actionStudentFragmentToDetailsFragment()
                requireView().findNavController().navigate(action)
            }

            R.id.sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val action = StudentFragmentDirections.actionStudentFragmentToGetStartedFragment()
                requireView().findNavController().navigate(action)
            }

        }
        return super.onOptionsItemSelected(item)


    }



}