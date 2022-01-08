package com.example.projectx.screens.Student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projectx.R
import com.example.projectx.daos.JoinClassDao
import com.example.projectx.databinding.FragmentJoinClassBinding
import com.example.projectx.databinding.FragmentStudentBinding

class JoinClassFragment : Fragment() {

    private var _binding: FragmentJoinClassBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJoinClassBinding.inflate(inflater, container, false)
        val view = binding!!.root
        binding.apply {
            classId.setText("32kY41V5dc1cvlCVDaRi")
            join.setOnClickListener {
                JoinClassDao().joinClass(classId.text.toString())
            }
        }
        return view
    }


}