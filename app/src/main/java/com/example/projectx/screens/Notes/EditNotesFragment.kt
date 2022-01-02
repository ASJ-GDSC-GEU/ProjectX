package com.example.projectx.screens.Notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projectx.R
import com.example.projectx.databinding.FragmentEditNotesBinding
import com.example.projectx.entities.Notes
import com.example.projectx.viewModel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*


class EditNotesFragment : Fragment() {

    val oldNotes by navArgs<EditNotesFragmentArgs>()
    lateinit var binding : FragmentEditNotesBinding
    var priority: String = "1"
    val viewModel : NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)

        binding.etTitle.setText(oldNotes.data.title)
        binding.etSubtitle.setText(oldNotes.data.subTitle)
        binding.etNoteBody.setText(oldNotes.data.notes)

        when(oldNotes.data.priority){
            "1" -> {
                binding.ivGreenDot.setImageResource(R.drawable.done)
                priority = "1"
                binding.ivRedDot.setImageResource(0)
                binding.ivYellowDot.setImageResource(0)
            }

            "2" -> {
                binding.ivYellowDot.setImageResource(R.drawable.done)
                priority = "2"
                binding.ivRedDot.setImageResource(0)
                binding.ivGreenDot.setImageResource(0)
            }
            "3" -> {
                binding.ivRedDot.setImageResource(R.drawable.done)
                priority = "3"
                binding.ivYellowDot.setImageResource(0)
                binding.ivGreenDot.setImageResource(0)
            }
        }

        binding.ivRedDot.setOnClickListener {
            binding.ivRedDot.setImageResource(R.drawable.done)
            priority = "3"
            binding.ivYellowDot.setImageResource(0)
            binding.ivGreenDot.setImageResource(0)
        }

        binding.ivGreenDot.setOnClickListener {
            binding.ivGreenDot.setImageResource(R.drawable.done)
            priority = "1"
            binding.ivRedDot.setImageResource(0)
            binding.ivYellowDot.setImageResource(0)
        }

        binding.ivYellowDot.setOnClickListener {
            binding.ivYellowDot.setImageResource(R.drawable.done)
            priority = "2"
            binding.ivRedDot.setImageResource(0)
            binding.ivGreenDot.setImageResource(0)
        }

        binding.saveNotes.setOnClickListener {
            updateNote()
        }
        return binding.root
    }

    private fun updateNote(){
        val title = binding.etTitle.text.toString()
        val subTitle = binding.etSubtitle.text.toString()
        val note = binding.etNoteBody.text.toString()


        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val notes = Notes(oldNotes.data.id, title, subTitle, note, currentDate, priority)
        viewModel.updateNote( notes)

        val action = EditNotesFragmentDirections.actionEditNotesFragmentToHomeNotesFragment()
        requireView().findNavController().navigate(action)
    }

}