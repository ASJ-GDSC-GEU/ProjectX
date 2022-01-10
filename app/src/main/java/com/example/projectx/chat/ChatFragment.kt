package com.example.projectx.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectx.databinding.FragmentChatFragmetBinding

class ChatFragment : Fragment() {
    private var _binding : FragmentChatFragmetBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatFragmetBinding.inflate(inflater, container, false)




        return binding.root
    }

}