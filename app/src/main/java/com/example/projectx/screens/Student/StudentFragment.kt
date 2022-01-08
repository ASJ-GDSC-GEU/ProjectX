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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.projectx.R
import com.example.projectx.adapter.MyClassAdapter
import com.example.projectx.daos.MyClassDao
import com.example.projectx.daos.TopDao
import com.example.projectx.databinding.FragmentStudentBinding
import com.example.projectx.models.MyClass
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase


class StudentFragment : Fragment() {

    private var _binding: FragmentStudentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyClassAdapter
    private val USER_TYPE: Int = 0 // 0 for student and 1 for Teacher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        val view = binding!!.root

        binding.apply {
            activity?.setActionBar(toolbar2)
            activity?.actionBar?.setDisplayShowTitleEnabled(false)
            val userImage = TopDao().currentUser().photoUrl
            Glide.with(view.context).load(userImage).circleCrop().error(R.drawable.user_error)
                .into(userLogo)
            setUpRecyclerView()
            joinMeetStu.setOnClickListener {
                navigateToMeetingHome()
            }
            studentFloat.setOnClickListener {
                popUpMenu(view)
            }
            userLogo.setOnClickListener {
                popUpMenuSetting(view)
            }
        }

        return view
    }


    private fun popUpMenuSetting(view: View) {
        val popupMenu: PopupMenu =
            PopupMenu(context, binding.userLogo, Gravity.END, 0, R.style.MyPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.mini_setting_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout ->
                    logoutUser()
                R.id.assignment_menu ->
                    navigateToStuAssignment()
                R.id.notes_menu ->
                    navigateToStuNotes()
            }
            true
        })
        popupMenu.show()
    }

    private fun logoutUser() {
        Firebase.auth.signOut()
            navigateToGetStarted()
    }




    private fun setUpRecyclerView() {
        var collection = MyClassDao().getClassCollection()
        var user_id = TopDao().userId()
        var query = collection.whereArrayContains("students_id", user_id)
            .orderBy("semester", Query.Direction.ASCENDING)
        val recyclerOptions =
            FirestoreRecyclerOptions.Builder<MyClass>().setQuery(query, MyClass::class.java).build()
        adapter = MyClassAdapter(recyclerOptions, USER_TYPE)
        binding.recyclerviewStu.adapter = adapter
        binding.recyclerviewStu.layoutManager = LinearLayoutManager(view?.context)
        adapter.startListening()
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

    private fun navigateToGetStarted() {
        val action = StudentFragmentDirections.actionStudentFragmentToGetStartedFragment()
        requireView().findNavController().navigate(action)
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

    private fun navigateToMeetingHome() {
        val action = StudentFragmentDirections.actionStudentFragmentToMeetingHome()
        requireView().findNavController().navigate(action)
    }




}