package com.example.projectx.screens.Student

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.projectx.R
import com.example.projectx.adapter.MyClassAdapter
import com.example.projectx.daos.MyClassDao
import com.example.projectx.daos.TeacherDao
import com.example.projectx.daos.TopDao
import com.example.projectx.databinding.FragmentStudentBinding
import com.example.projectx.models.MyClass
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class StudentFragment : Fragment() {

    private var _binding: FragmentStudentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MyClassAdapter
    private val USER_TYPE: Int = 0 // 0 for student and 1 for Teacher
    private lateinit var teacherDao : TeacherDao
    private lateinit var topDao : TopDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        val view = binding!!.root
        topDao = TopDao()

        binding.apply {
            activity?.setActionBar(toolbar2)
            activity?.actionBar?.setDisplayShowTitleEnabled(false)
            val userImage = TopDao().currentUser().photoUrl
            Glide.with(view.context).load(userImage).circleCrop().error(R.drawable.user_error)
                .into(userLogo)
            setUpRecyclerView()
            userLogo.setOnClickListener {
                popUpMenuSetting(view)
            }

            binding.joinMeet.setOnClickListener {
                navigateToMeetingHome()

            }

            binding.assignmentButton.setOnClickListener {
                navigateToStuAssignment()

            }

            binding.notesButton.setOnClickListener {
                navigateToStuNotes()
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
                R.id.switch_to_teacher ->
                    navigateToTeacherFragment()

                R.id.join_class ->
                    navigateToJoinClass()

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
        var query = collection.whereArrayContains("students_id", "qxCjRDWLeUPd5EpztIq5pM43LlD2")
        val recyclerOptions =
            FirestoreRecyclerOptions.Builder<MyClass>().setQuery(query, MyClass::class.java).build()
        adapter = MyClassAdapter(recyclerOptions, USER_TYPE, requireContext())
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter.startListening()

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

    private fun navigateToDetailsFragment() {
        val action = StudentFragmentDirections.actionStudentFragmentToDetailsFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToTeacherFragment(){
        binding.mainCons.visibility = View.INVISIBLE
        binding.loaderCons.visibility = View.VISIBLE
        teacherDao = TeacherDao()
                    val snapshot2 =
                        teacherDao.getTeacherById(topDao.userId())
                            .addOnCompleteListener {
                                var teacherResult = it.result.exists()
                                if (!teacherResult) {
                                        navigateToDetailsFragment()
                                } else {
                                    val action =
                                        StudentFragmentDirections.actionStudentFragmentToTeachersFragment()
                                    requireView().findNavController().navigate(action)
                                }
                            }


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