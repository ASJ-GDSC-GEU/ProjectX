package com.example.projectx.screens

import android.annotation.SuppressLint
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
import com.example.projectx.R
import com.example.projectx.adapter.ClassOptionsAdapter
import com.example.projectx.adapter.StudentItemAdapter
import com.example.projectx.daos.TopDao
import com.example.projectx.databinding.FragmentClassGroupBinding
import com.example.projectx.models.ClassOptions
import com.example.projectx.models.MyClass
import com.example.projectx.models.StudentItem
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.toObject


class ClassGroupFragment : Fragment() {
    private var _binding: FragmentClassGroupBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StudentItemAdapter
    private var studentArray = ArrayList<StudentItem>()
    private var userType: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassGroupBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val classId: String = arguments?.getString("test").toString()
        userType = requireArguments().getInt("user_type")
        when (userType) {
            0 -> {
                binding.classOptions.visibility = View.GONE
            }
            1 -> {
                binding.classOptions.visibility = View.VISIBLE
                addOptions(classId)
            }
        }


        TopDao().dbRef().collection("classes")
            .document(classId)
            .get()
            .addOnSuccessListener { documents ->
                val data = documents.toObject<MyClass>()!!
                val array: List<String> = data.students_id
                binding.studentCount.text = array.size.toString() + " Students"
                binding.courseName.text = data.subject
                binding.courseSem.text = "${data.course} Sem-${data.semester}"
                if (array.isNotEmpty()) {
                    setRecyclerView(array)
                } else {
                    binding.progressBar2.visibility = View.GONE
                }
            }

        binding.settings.setOnClickListener {
            popUpMenuClassGroup(view, classId)
        }


    }

    private fun popUpMenuClassGroup(view: View, classId: String) {
        val userId = TopDao().userId()
        val popupMenu: PopupMenu =
            PopupMenu(context, binding.settings, Gravity.END, 0, R.style.MyPopupMenu)
        popupMenu.menuInflater.inflate(R.menu.class_group_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.exit_group ->
                    TopDao().dbRef().collection("classes").document(classId)
                        .update("students_id", FieldValue.arrayRemove(userId))
                        .addOnCompleteListener {
                            when (userType) {
                                0 -> {
                                   navigateToStudent()
                                }
                                1 -> {
                                    navigateToTeacher()
                                }
                            }
                        }

            }
            true
        })
        popupMenu.show()
    }

    private fun navigateToTeacher() {
        val action =
            ClassGroupFragmentDirections.actionClassGroupFragmentToTeachersFragment()
        requireView().findNavController().navigate(action)
    }

    private fun navigateToStudent() {
        val action =
            ClassGroupFragmentDirections.actionClassGroupFragmentToStudentFragment()
        requireView().findNavController().navigate(action)
    }

    private fun setRecyclerView(array: List<String>) {
        TopDao().dbRef().collection("student")
            .whereIn("uid", array)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val studentItem = StudentItem(
                        name = document.get("name").toString(),
                        description = document.get("course").toString(),
                        imageUrl = document.get("imageUrl").toString()
                    )
                    studentArray.add(studentItem)
                }
                adapter = StudentItemAdapter(studentArray, requireView().context)
                binding.progressBar2.visibility = View.GONE
                binding.recyclerViewStudents.visibility = View.VISIBLE
                binding.recyclerViewStudents.adapter = adapter
                binding.recyclerViewStudents.layoutManager = LinearLayoutManager(view?.context)
            }
    }


    private fun addOptions(classId: String) {
        binding.apply {
            classOptions.layoutManager =
                LinearLayoutManager(
                    requireView().context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            val data = ArrayList<ClassOptions>()
            data.add(
                ClassOptions(
                    label = "Create meet link",
                    imageView = R.drawable.create_meetlink_icon
                )
            )
            data.add(
                ClassOptions(
                    label = "Invite Students",
                    imageView = R.drawable.invite_student_icon
                )
            )
            data.add(
                ClassOptions(
                    label = "Share Files",
                    imageView = R.drawable.share_file_icon
                )
            )
            val adapter = ClassOptionsAdapter(classId, data)
            classOptions.adapter = adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
