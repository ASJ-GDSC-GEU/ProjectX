package com.example.projectx.screens

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectx.R
import com.example.projectx.adapter.MyClassAdapter
import com.example.projectx.daos.MyClassDao
import com.example.projectx.databinding.FragmentTeachersBinding
import com.example.projectx.models.MyClass
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase

class TeachersFragment : Fragment() {
    private var _binding: FragmentTeachersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyClassAdapter
    private lateinit var classes: List<MyClass>
    private lateinit var myClassDao : MyClassDao
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeachersBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.createMeet.setOnClickListener {
            val action = TeachersFragmentDirections.actionTeachersFragmentToMeetingsActivity()
            requireView().findNavController().navigate(action)
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setUpRecyclerView()


        }

        binding.create.setOnClickListener {
            //popupMenu
            popUpMenu(view)
        }

    }

    private fun setUpRecyclerView() {
        var collection = MyClassDao().getClassCollection()
        var query = collection.orderBy("semester", Query.Direction.ASCENDING)
        val recyclerOptions = FirestoreRecyclerOptions.Builder<MyClass>().setQuery(query, MyClass::class.java).build()
        adapter = MyClassAdapter(recyclerOptions)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(view?.context)
    }

    private fun popUpMenu(view: View) {
        val popupMenu: PopupMenu =
            PopupMenu(context, binding.create, Gravity.END, 0, R.style.MyPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.create_fabmenu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.create_newClass ->
                    openDialog(view)
                R.id.create_meet ->
                    Toast.makeText(view.context, "Create Meet", Toast.LENGTH_SHORT).show()
                R.id.create_timeTable ->
                    findNavController().navigate(R.id.homeNotesFragment)
            }
            true
        })
        popupMenu.show()
    }

    private fun openDialog(view: View) {
        val dialog = BottomSheetDialog(view.context)
        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet, null)
        val cources = resources.getStringArray(R.array.Cources)
        val subject = resources.getStringArray(R.array.Subject)
        val semester = resources.getStringArray(R.array.Semester)
        val section = resources.getStringArray(R.array.Section)
        val courcesAdapter = ArrayAdapter(view.context, R.layout.dropdown_item, cources)
        val subAdapter = ArrayAdapter(view.context, R.layout.dropdown_item, subject)
        val semAdapter = ArrayAdapter(view.context, R.layout.dropdown_item, semester)
        val secAdapter = ArrayAdapter(view.context, R.layout.dropdown_item, section)
        val courceView = view.findViewById<MaterialAutoCompleteTextView>(R.id.course_drop)
        val subjectView = view.findViewById<MaterialAutoCompleteTextView>(R.id.subjet_drop)
        val semView = view.findViewById<MaterialAutoCompleteTextView>(R.id.semester_drop)
        val secView = view.findViewById<MaterialAutoCompleteTextView>(R.id.section_drop)
        val create_button = view.findViewById<Button>(R.id.create_button)
        val cancel_button = view.findViewById<Button>(R.id.cancel_button)
        courceView.setAdapter(courcesAdapter)
        subjectView.setAdapter(subAdapter)
        semView.setAdapter(semAdapter)
        secView.setAdapter(secAdapter)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        create_button.setOnClickListener {
            val myClass = MyClass(
                courceView.text.toString(),
                subjectView.text.toString(),
                semView.text.toString(),
                secView.text.toString(),
                Firebase.auth.currentUser!!.uid
            )
            val myClassDao = MyClassDao()
            myClassDao.addClass(myClass)
            dialog.dismiss()
        }
        cancel_button.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    private fun addTaskCalendarDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Add Task")
            .setMessage("Choose Date and Time")
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


}